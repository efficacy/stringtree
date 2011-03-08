package org.stringtree.util.sort;

import java.util.Iterator;
import java.util.Vector;

import org.stringtree.util.iterator.AbstractIterator;

/**
 * An Iterator which is a sorted version of another Iterator
 */
public class SortedIteratorIterator<T> extends AbstractIterator<T> {
    
    protected Sorter<T> sorter;
    protected Vector<T> elements;
    protected Iterator<T> it;

    protected SortedIteratorIterator(Iterator<T> it, boolean unique) {
        sorter = new Sorter<T>();
        elements = new Vector<T>();

        if (it != null) {
            while (it.hasNext()) {
                T item = it.next();
                if (!unique || !elements.contains(item))
                    elements.addElement(item);
            }
        }
        sorter.sortVector(elements);

        this.it = elements.iterator();
    }

    public SortedIteratorIterator(Iterator<T> it) {
        this(it, false);
    }

    public boolean hasNext() {
        return it.hasNext();
    }

    public T next() {
        return it.next();
    }
}