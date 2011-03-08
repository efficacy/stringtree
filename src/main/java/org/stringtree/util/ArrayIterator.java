package org.stringtree.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {
    
    private T[] array;
    private int i;

    public ArrayIterator(T... array) {
        this.array = array;
        i = 0;
    }

    public boolean hasNext() {
        return array != null ? i < array.length : false;
    }

    public T next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return array[i++];
    }

    public void remove() {
        throw new UnsupportedOperationException(this.getClass().getName()
                + " doesn't support remove");
    }
}