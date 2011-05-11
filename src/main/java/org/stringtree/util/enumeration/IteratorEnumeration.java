package org.stringtree.util.enumeration;

import java.util.Enumeration;
import java.util.Iterator;

@SuppressWarnings("rawtypes")
public class IteratorEnumeration implements Enumeration {
    
	private Iterator iterator;

	public IteratorEnumeration(Iterator iterator) {
		this.iterator = iterator;
	}
	
	public boolean hasMoreElements() {
		return iterator.hasNext();
	}

	public Object nextElement() {
		return iterator.next();
	}
}
