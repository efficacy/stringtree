package org.stringtree.fetcher;

import org.stringtree.Container;
import org.stringtree.Fetcher;
import org.stringtree.Tract;

public class SingleStringFetcher implements Fetcher, Container {
    
    protected String key;
    protected String value;

    public SingleStringFetcher(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public SingleStringFetcher(String value) {
        this(Tract.CONTENT, value);
    }

    public boolean contains(String key) {
        return Tract.CONTENT.equals(key) || key.equals(this.key);
    }

    public Object getObject(String name) {
        return (key.equals(name) || Tract.CONTENT.equals(name)) ? value : null;
    }
}