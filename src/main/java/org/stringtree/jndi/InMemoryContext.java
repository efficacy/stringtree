package org.stringtree.jndi;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.CompoundName;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NotContextException;
import javax.naming.OperationNotSupportedException;
import javax.naming.spi.NamingManager;

@SuppressWarnings({"unchecked", "rawtypes"})
public class InMemoryContext implements Context {
    
	protected static final NameParser myParser = new NameParser() {
		private final Properties syntax = new Properties(); 
        {
	        syntax.put("jndi.syntax.direction", "left_to_right");
			syntax.put("jndi.syntax.separator", "/");
	        syntax.put("jndi.syntax.ignorecase", "false");
			syntax.put("jndi.syntax.escape", "\\");
			syntax.put("jndi.syntax.beginquote", "'");
	    }
		
		public Name parse(String name) throws NamingException {
	        return new CompoundName(name, syntax);
	    }
	};

	protected Hashtable env = null;
	protected Hashtable bindings = new Hashtable();
	protected InMemoryContext parent = null;
	protected String myAtomicName = null;

	public InMemoryContext(Hashtable env) {
		this.env = (env != null) ? (Hashtable)(env.clone()) : null;
	}
	
	public InMemoryContext() {
		this(null);
	}

	protected InMemoryContext(InMemoryContext parent, String name, Hashtable env, Hashtable bindings) {
		this(env);
		this.parent = parent;
		myAtomicName = name;
		this.bindings = (Hashtable)bindings.clone();
	}

	protected Context createCtx(InMemoryContext parent, String name, Hashtable env) {
		return new InMemoryContext(parent, name, env, new Hashtable(11));
	}

	protected Context cloneCtx() {
		return new InMemoryContext(parent, myAtomicName, env, bindings);
	}

	/**
	* Utility method for processing composite/compound name.
	* @param name The non-null composite or compound name to process.
	* @return The non-null string name in this namespace to be processed.
	*/
	protected Name getMyComponents(Name name) {
		// Already parsed
		return name;
	}

	public Object lookup(String name) throws NamingException {
		return lookup(new CompositeName(name));
	}

	public Object lookup(Name name) throws NamingException {
		if (name.isEmpty()) {
			// Asking to look up this context itself.  Create and return
			// a new instance with its own independent environment.
			return cloneCtx();
		} 

		// Extract components that belong to this namespace
		Name nm = getMyComponents(name);
		String atom = nm.get(0);
		Object inter = bindings.get(atom);

		if (nm.size() == 1) {
			// Atomic name: Find object in internal data structure
			if (inter == null) {
				throw new NameNotFoundException(name + " not found");
			}

			// Call getObjectInstance for using any object factories
			try {
				return NamingManager.getObjectInstance(inter, new CompositeName().add(atom), this, env);
			} catch (Exception e) {
				throw new NamingExceptionWrapper("getObjectInstance failed", e);
			}
		} 

		// Intermediate name: Consume name in this context and continue
		if (!(inter instanceof Context)) {
			throw new NotContextException(atom + " does not name a context");
		}

		return ((Context)inter).lookup(nm.getSuffix(1));
	}

	public void bind(String name, Object obj) throws NamingException {
		bind(new CompositeName(name), obj);
	}

    public void bind(Name name, Object obj) throws NamingException {
		if (name.isEmpty()) {
			throw new InvalidNameException("Cannot bind empty name");
		}

		// Extract components that belong to this namespace
		Name nm = getMyComponents(name);
		String atom = nm.get(0);
		Object inter = bindings.get(atom);

		if (nm.size() == 1) {
			// Atomic name: Find object in internal data structure
			if (inter != null) {
				throw new NameAlreadyBoundException("Use rebind to override");
			}

			// Call getStateToBind for using any state factories
			obj = NamingManager.getStateToBind(obj, new CompositeName().add(atom), this, env);

			// Add object to internal data structure
			bindings.put(atom, obj);
		} else {
			// Intermediate name: Consume name in this context and continue
			if (!(inter instanceof Context)) {
				throw new NotContextException(atom + " does not name a context");
			}
			((Context)inter).bind(nm.getSuffix(1), obj);
		}
	}

	public void rebind(String name, Object obj) throws NamingException {
		rebind(new CompositeName(name), obj);
	}

	public void rebind(Name name, Object obj) throws NamingException {
		if (name.isEmpty()) {
			throw new InvalidNameException("Cannot bind empty name");
		}

		// Extract components that belong to this namespace
		Name nm = getMyComponents(name);
		String atom = nm.get(0);

		if (nm.size() == 1) {
			// Atomic name

			// Call getStateToBind for using any state factories
			obj = NamingManager.getStateToBind(obj, new CompositeName().add(atom), this, env);

			// Add object to internal data structure
			bindings.put(atom, obj);
		} else {
			// Intermediate name: Consume name in this context and continue
			Object inter = bindings.get(atom);
			if (!(inter instanceof Context)) {
				throw new NotContextException(atom + 	" does not name a context");
			}
			((Context)inter).rebind(nm.getSuffix(1), obj);
		}
	}

	public void unbind(String name) throws NamingException {
		unbind(new CompositeName(name));
	}

	public void unbind(Name name) throws NamingException {
		if (name.isEmpty()) {
			throw new InvalidNameException("Cannot unbind empty name");
		}

		// Extract components that belong to this namespace
		Name nm = getMyComponents(name);
		String atom = nm.get(0);

		// Remove object from internal data structure
		if (nm.size() == 1) {
			// Atomic name: Find object in internal data structure
			bindings.remove(atom);
		} else {
			// Intermediate name: Consume name in this context and continue
			Object inter = bindings.get(atom);
			if (!(inter instanceof Context)) {
				throw new NotContextException(atom + " does not name a context");
			}
			((Context)inter).unbind(nm.getSuffix(1));
		}
	}

	public void rename(String oldname, String newname) throws NamingException {
		rename(new CompositeName(oldname), new CompositeName(newname));
	}

	public void rename(Name oldname, Name newname) throws NamingException {
		if (oldname.isEmpty() || newname.isEmpty()) {
			throw new InvalidNameException("Cannot rename empty name");
		}

		// Extract components that belong to this namespace
		Name oldnm = getMyComponents(oldname);
		Name newnm = getMyComponents(newname);

		// Simplistic implementation: support only rename within same context
		if (oldnm.size() != newnm.size()) {
		    throw new OperationNotSupportedException("Do not support rename across different contexts");
		}

		String oldatom = oldnm.get(0);
		String newatom = newnm.get(0);

		if (oldnm.size() == 1) {
			// Atomic name: Add object to internal data structure
			// Check if new name exists
			if (bindings.get(newatom) != null) {
				throw new NameAlreadyBoundException(newname.toString() + " is already bound");
			}

			// Check if old name is bound
			Object oldBinding = bindings.remove(oldatom);
			if (oldBinding == null) {
				throw new NameNotFoundException(oldname.toString() + " not bound");
			}

			bindings.put(newatom, oldBinding);
		} else {
			// Simplistic implementation: support only rename within same context
			if (!oldatom.equals(newatom)) {
				throw new OperationNotSupportedException("Do not support rename across different contexts");
			}

			// Intermediate name: Consume name in this context and continue
			Object inter = bindings.get(oldatom);
			if (!(inter instanceof Context)) {
				throw new NotContextException(oldatom + " does not name a context");
			}
			((Context)inter).rename(oldnm.getSuffix(1), newnm.getSuffix(1));
		}
	}

	public NamingEnumeration list(String name) throws NamingException {
		return list(new CompositeName(name));
	}

	public NamingEnumeration list(Name name) throws NamingException {
		if (name.isEmpty()) { 
			// listing this context
			return new ListOfNames(bindings.keys());
		} 

		// Perhaps 'name' names a context
		Object target = lookup(name);
		if (target instanceof Context) {
			return ((Context)target).list("");
		}

		throw new NotContextException(name + " cannot be listed");
	}

	public NamingEnumeration listBindings(String name) throws NamingException {
		return listBindings(new CompositeName(name));
	}

	public NamingEnumeration listBindings(Name name) throws NamingException {
		if (name.isEmpty()) {
    		// listing this context
    		return new ListOfBindings(bindings.keys());
		} 

		// Perhaps 'name' names a context
		Object target = lookup(name);
		if (target instanceof Context) {
			return ((Context)target).listBindings("");
		}
		throw new NotContextException(name + " cannot be listed");
	}

	public void destroySubcontext(String name) throws NamingException {
		destroySubcontext(new CompositeName(name));
	}

	public void destroySubcontext(Name name) throws NamingException {
		if (name.isEmpty()) {
			throw new InvalidNameException("Cannot destroy context using empty name");
		}

		// Simplistic implementation: not checking for nonempty context first
		// Use same implementation as unbind
		unbind(name);
	}

	public Context createSubcontext(String name) throws NamingException {
		return createSubcontext(new CompositeName(name));
	}

	public Context createSubcontext(Name name) throws NamingException {
		if (name.isEmpty()) {
			throw new InvalidNameException("Cannot bind empty name");
		}

		// Extract components that belong to this namespace
		Name nm = getMyComponents(name);
		String atom = nm.get(0);
		Object inter = bindings.get(atom);

		if (nm.size() == 1) {
			// Atomic name: Find object in internal data structure
			if (inter != null) {
				throw new NameAlreadyBoundException("Use rebind to override");
			}

			// Create child
			Context child = createCtx(this, atom, env);

			// Add child to internal data structure
			bindings.put(atom, child);

			return child;
		} 

		// Intermediate name: Consume name in this context and continue
		if (!(inter instanceof Context)) {
			throw new NotContextException(atom + " does not name a context");
		}
		return ((Context)inter).createSubcontext(nm.getSuffix(1));
	}

	public Object lookupLink(String name) throws NamingException {
		return lookupLink(new CompositeName(name));
	}

	public Object lookupLink(Name name) throws NamingException {
		return lookup(name);
	}

	public NameParser getNameParser(String name) throws NamingException {
		return getNameParser(new CompositeName(name));
	}

	public NameParser getNameParser(Name name) throws NamingException {
		// Do lookup to verify name exists
		Object obj = lookup(name);
		if (obj instanceof Context) {
			((Context)obj).close();
		}
		return myParser;
	}

	public String composeName(String name, String prefix) throws NamingException {
		Name result = composeName(new CompositeName(name), new CompositeName(prefix));
		return result.toString();
	}

	public Name composeName(Name name, Name prefix) throws NamingException {
		Name result;

		// Both are compound names, compose using compound name rules
		if (!(name instanceof CompositeName) && !(prefix instanceof CompositeName)) {
			result = (Name)(prefix.clone());
			result.addAll(name);
			return new CompositeName().add(result.toString());
		}

		// Simplistic implementation: do not support federation
		throw new OperationNotSupportedException("Do not support composing composite names");
	}

	public Object addToEnvironment(String propName, Object propVal) throws NamingException {
		if (env == null) {
			env = new Hashtable(5, 0.75f);
		} 
		return env.put(propName, propVal);
	}

	public Object removeFromEnvironment(String propName) throws NamingException {
		if (env == null) {
			return null;
		}
		return env.remove(propName);
	}

	public Hashtable getEnvironment() throws NamingException {
		return (env == null) ? new Hashtable(3, 0.75f) : (Hashtable)env.clone();
	}

	public String getNameInNamespace() throws NamingException {
		InMemoryContext ancestor = parent;

		// No ancestor
		if (ancestor == null) {
			return "";
		}

		Name name = myParser.parse("");
		name.add(myAtomicName);

		// Get parent's names
		while (ancestor != null && ancestor.myAtomicName != null) {
			name.add(0, ancestor.myAtomicName);
			ancestor = ancestor.parent;
		}

		return name.toString();
	}

	public String toString() {
		return (myAtomicName != null) ? myAtomicName : "ROOT CONTEXT";
	}

	public void close() throws NamingException {
			// this method deliberately left blank
	}
	
	public void clear() {
		bindings.clear();
	}

	// Class for enumerating name/class pairs
	class ListOfNames implements NamingEnumeration {
        
		protected Enumeration names;

		public ListOfNames (Enumeration names) {
			this.names = names;
		}

		public boolean hasMoreElements() {
			try {
				return hasMore();
			} catch (NamingException e) {
				return false;
			}
		}

		public boolean hasMore() throws NamingException	{
			return names.hasMoreElements();
		}

		public Object next() throws NamingException {
			String name = (String)names.nextElement();
			String className = bindings.get(name).getClass().getName();
			return new NameClassPair(name, className);
		}

		public Object nextElement() {
			try {
				return next();
			} catch (NamingException e) {
				throw new NoSuchElementException(e.toString());
			}
		}

		public void close() {
			env.clear();
			bindings.clear();
		}
	}

	class ListOfBindings extends ListOfNames {
		public ListOfBindings(Enumeration names) {
			super(names);
		}

		public Object next() throws NamingException {
			String name = (String)names.nextElement();
			Object obj = bindings.get(name);

			try {
				obj = NamingManager.getObjectInstance(obj, new CompositeName().add(name), 
					InMemoryContext.this, InMemoryContext.this.env);
			} catch (Exception e) {
				throw new NamingExceptionWrapper("getObjectInstance failed", e);
			}

			return new Binding(name, obj);
		}
	}
}
