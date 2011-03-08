package tests.jndi;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.stringtree.jndi.InMemoryContext;

public class ContextTest extends TestCase
{
	private static final String sep = "/";
	
	private void checkContext(Context ctx) throws NamingException {
        ctx.unbind("a");
        
		Context a = ctx.createSubcontext("a");
		Context b = a.createSubcontext("b");
		Context c = b.createSubcontext("c");

		assertEquals("a" + sep + "b" + sep + "c", c.getNameInNamespace());
		assertEquals("ROOT CONTEXT", ctx.lookup("").toString());
		assertEquals("a", ctx.lookup("a").toString());
		assertEquals("b", ctx.lookup("a" + sep + "b").toString());
		assertEquals("c", a.lookup("b" + sep + "c").toString());
		
		b.bind("size", Integer.valueOf(56));
		assertEquals(Integer.valueOf(56), b.lookup("size"));
        assertEquals(Integer.valueOf(56), a.lookup("b/size"));
        assertEquals(Integer.valueOf(56), ctx.lookup("a/b/size"));
	}
	
	public void testLocal()	throws NamingException {
		checkContext(new InMemoryContext());
	}
	
	public void testFactory() throws NamingException {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.stringtree.jndi.InMemoryContextFactory"); 
		
		checkContext(new InitialContext(props));
	}
	
	public void testSystemFactory() throws NamingException {
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.stringtree.jndi.InMemoryContextFactory"); 
		
		checkContext(new InitialContext());
	}
}
