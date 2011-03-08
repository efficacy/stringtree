package org.stringtree.finder;

import java.util.Iterator;

import org.stringtree.Fetcher;

public abstract class DegenerateStringFinder implements StringFinder {

    public Object getObject(String name) {
        return get(name);
    }

    public Fetcher getUnderlyingFetcher() {
        return null;
    }

    public Iterator<String> list() {
        return null;
    }

    public boolean contains(String name) {
        return false;
    }
}
