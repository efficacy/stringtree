package org.stringtree.util.iterator;

import java.util.NoSuchElementException;

public class SingleItemIterator<T> extends AbstractIterator<T> {
    
    private final T item;
    private boolean done;

    public SingleItemIterator(T item) {
        this.item = item;
        done = false;
    }

    public boolean hasNext() {
        return !done;
    }

    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        done = true;
        return item;
    }
}
