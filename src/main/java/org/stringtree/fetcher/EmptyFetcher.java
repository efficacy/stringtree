package org.stringtree.fetcher;

import org.stringtree.Container;
import org.stringtree.Fetcher;

public class EmptyFetcher implements Fetcher, Container {
    
    public static final EmptyFetcher it = new EmptyFetcher();

    public Object getObject(String key) {
        return null;
    }

    public boolean contains(String key) {
        return false;
    }
}