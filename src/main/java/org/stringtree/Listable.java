package org.stringtree;

import java.util.Iterator;

public interface Listable<T> {
    static final String LIST = "fetcher.listable";
    Iterator<T> list();
}