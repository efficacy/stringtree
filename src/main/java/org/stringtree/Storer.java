package org.stringtree;

public interface Storer {
    static final String STORE = "fetcher.storer";

    void put(String name, Object value);
    void remove(String name);
    void clear();
    void lock();
    boolean isLocked();
}
