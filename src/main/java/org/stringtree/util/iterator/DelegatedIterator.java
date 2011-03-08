package org.stringtree.util.iterator;

import java.util.Iterator;

import org.stringtree.util.Delegator;

public class DelegatedIterator<T> extends Delegator implements Iterator<T> {

    public DelegatedIterator(Iterator<T> other) {
        super(other);
    }
    
    @SuppressWarnings("unchecked")
	protected Iterator<T> realIterator() {
        return (Iterator<T>)other;
    }

    public boolean hasNext() {
        return realIterator().hasNext();
    }

    public T next() {
        return realIterator().next();
    }

    public void remove() {
        realIterator().remove();
    }
}
