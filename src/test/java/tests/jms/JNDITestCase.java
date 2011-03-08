package tests.jms;

import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;

import junit.framework.TestCase;

public abstract class JNDITestCase extends TestCase {
    
    {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.stringtree.jndi.InMemoryContextFactory"); 
        org.stringtree.jndi.InMemoryContextFactory.clear();
    }

	public Context ensureSubcontext(Context root, String name) throws NamingException {
        Context ret;
        
        try {
        	ret = root.createSubcontext(name);
        } catch (NameAlreadyBoundException nabe) {
        	ret = (Context) root.lookup(name);
        }
        
        return ret;
	}

	protected void ensureJNDI(String name, Object value) throws NamingException {
		Context context = new InitialContext();
		StringTokenizer tok = new StringTokenizer(name, "/.");
		String token = null;
		while (tok.hasMoreTokens()) {
			token = tok.nextToken();
			if (tok.hasMoreTokens()) {
				ensureSubcontext(context, token);
			}
		}
		
		if (token != null) {
			context.rebind(name, value);
		}
	}

    public Context ensureSubcontext(String name) throws NamingException {
        return ensureSubcontext(new InitialContext(), name);
    }
}
