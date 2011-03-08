package org.stringtree.util.sort;

import java.util.Iterator;

/**
 * A sorted version of another Iterator which skips duplicates
 */
public class UniqueSortedIteratorIterator<T> extends SortedIteratorIterator<T> {
    
    public UniqueSortedIteratorIterator(Iterator<T> it) {
        super(it, true);
    }
}