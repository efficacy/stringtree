package org.stringtree.util.testing;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import org.stringtree.util.ArrayIterator;
import org.stringtree.util.Utils;
import org.stringtree.util.iterator.EnumerationIterator;
import org.stringtree.util.iterator.IteratorCollection;

public class ChecklistOrdered<T> {
    private T[] items;
    private int verbose;

    @SuppressWarnings("unchecked")
    private ChecklistOrdered(int size) {
        this.items = (T[]) new Object[size];
    }
    
    public ChecklistOrdered(Collection<T> collection, int verbose) {
        this(collection.size());
        int i = 0;
        for(T t : collection) {
            this.items[i++] = t;
        }
        this.verbose = verbose;
    }
    
    public ChecklistOrdered(Collection<T> collection) {
        this(collection, Checklist.NORMAL);
    }

    public ChecklistOrdered(int verbose, T... array) {
        this(array.length);
        int i = 0;
        for(T t : array) {
            this.items[i++] = t;
        }
        this.verbose = verbose;
    }
    
    public ChecklistOrdered(T... array) {
        this(Checklist.NORMAL, array);
    }

    public ChecklistOrdered(Iterator<T> iterator, int verbose) {
        this(new IteratorCollection<T>(iterator), verbose);
    }

    public ChecklistOrdered(Enumeration<T> enumeration, int verbose) {
        this(new IteratorCollection<T>(new EnumerationIterator<T>(enumeration)), verbose);
    }

    public boolean check(Iterator<T> it) {
        int index = 0;
        while (it.hasNext()) {
            if (index >= items.length) {
                if (verbose > Checklist.SILENT) {
                    System.out.println("Checklist Failure too many items, expected " + items.length);
                }
                return false;
            }
            
            T value = it.next();
            if (!compare(index, value)) {
                return false;
            }
            ++index;
        }
        
        if (index < items.length) {
            if (verbose > Checklist.SILENT) {
                System.out.println("Checklist Failure too few items, expected " + items.length);
            }
            return false;
        }
        
        return true;
    }

    public boolean check(Enumeration<T> it) {
        return check(new EnumerationIterator<T>(it));
    }

    public boolean check(Collection<T> c) {
        return check(c.iterator());
    }

    public boolean check(T[] array) {
        return check(new ArrayIterator<T>(array));
    }

    private boolean compare(int index, T value) {
        if (!Utils.same(items[index], value)) {
            if (verbose > Checklist.SILENT) {
                System.out.println("Checklist Failure at index " + index + " expected '" + items[index] + "' was  '" + value + "'");
            }
            return false;
        }
        if (verbose > Checklist.NORMAL) {
            System.out.println("Checklist Match at index " + index + " expected '" + items[index] + "' was  '" + value + "'");
        }
        return true;
    }
}
