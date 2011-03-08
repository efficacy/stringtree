package org.stringtree.util.iterator;

import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class EmptyIterator extends AbstractIterator {
    
    private static EmptyIterator it = new EmptyIterator();

    public boolean hasNext() {
        return false;
    }

    public Object next() {
        throw new NoSuchElementException();
    }

    public static EmptyIterator it() {
        return it;
    }
}
