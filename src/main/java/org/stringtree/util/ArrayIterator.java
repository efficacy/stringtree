package org.stringtree.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {

    private T[] array;
    private int i;

    @SafeVarargs
    public ArrayIterator(T... array) {
        this.array = array;
        i = 0;
    }

    @Override public boolean hasNext() {
        return array != null ? i < array.length : false;
    }

    @Override public T next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return array[i++];
    }

    @Override public void remove() {
        throw new UnsupportedOperationException(this.getClass().getName()
                + " doesn't support remove");
    }
}