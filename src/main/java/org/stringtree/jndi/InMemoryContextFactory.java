package org.stringtree.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.spi.InitialContextFactory;

public class InMemoryContextFactory implements InitialContextFactory {
    
    private static InMemoryContext singleton = new InMemoryContext();
    
	@SuppressWarnings("unchecked")
	public Context getInitialContext(Hashtable env) {
		return singleton;
	}
	
	public static void clear() {
		singleton.clear();
	}
}
