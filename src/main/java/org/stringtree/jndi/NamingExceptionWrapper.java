package org.stringtree.jndi;

import javax.naming.NamingException;

public class NamingExceptionWrapper extends NamingException {
    
	public NamingExceptionWrapper(String message, Throwable rootCause) {
		super(message);
		super.setRootCause(rootCause);
	}
}

