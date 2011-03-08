package org.stringtree.util.iterator;

import java.util.Iterator;

public abstract class AbstractIterator<T> implements Iterator<T>, Iterable<T> {
    
    public abstract boolean hasNext();

    public abstract T next();

    public void remove() {
        throw new UnsupportedOperationException(this.getClass().getName()
                + " does not support remove");
    }
    
    public Iterator<T> iterator() {
        return this;
    }
}
