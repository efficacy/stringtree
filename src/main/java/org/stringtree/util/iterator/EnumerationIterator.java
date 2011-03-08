package org.stringtree.util.iterator;

import java.util.Enumeration;

public class EnumerationIterator<T> extends AbstractIterator<T> {
    
    private final Enumeration<T> e;

    public EnumerationIterator(Enumeration<T> e) {
        this.e = e;
    }

    public boolean hasNext() {
        return e.hasMoreElements();
    }

    public T next() {
        return e.nextElement();
    }
}