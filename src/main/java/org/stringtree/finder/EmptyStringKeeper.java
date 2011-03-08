package org.stringtree.finder;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.EmptyFetcher;

public class EmptyStringKeeper implements StringKeeper {

    private static final EmptyFetcher emptyFetcher = new EmptyFetcher();
    public static final EmptyStringKeeper emptyStringKeeper = new EmptyStringKeeper();

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

    public void clear() {
    }

    public boolean isLocked() {
        return true;
    }

    public void lock() {
    }

    public void put(String name, Object value) {
    }

    public void remove(String name) {
    }
}
