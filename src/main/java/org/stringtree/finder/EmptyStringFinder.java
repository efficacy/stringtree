package org.stringtree.finder;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.EmptyFetcher;

public class EmptyStringFinder implements StringFinder {

    private static final EmptyFetcher emptyFetcher = new EmptyFetcher();
    public static final EmptyStringFinder it = new EmptyStringFinder();

    public String get(String name) {
        return "";
    }

    public Object getObject(String name) {
        return null;
    }

    public Fetcher getUnderlyingFetcher() {
        return emptyFetcher;
    }

    public boolean contains(String name) {
        return false;
    }
}
