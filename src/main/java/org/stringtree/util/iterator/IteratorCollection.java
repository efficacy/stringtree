package org.stringtree.util.iterator;

import java.util.ArrayList;
import java.util.Iterator;

public class IteratorCollection<T> extends ArrayList<T> {
    public IteratorCollection(Iterator<T> iterator) {
        while (iterator.hasNext()) {
            add(iterator.next());
        }
    }
}
