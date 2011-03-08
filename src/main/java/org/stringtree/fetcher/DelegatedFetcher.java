package org.stringtree.fetcher;

import org.stringtree.Fetcher;
import org.stringtree.util.Delegator;

public class DelegatedFetcher extends Delegator implements Fetcher {
    
    public DelegatedFetcher(Fetcher other) {
        super(other);
    }

    protected DelegatedFetcher() {
        super(null);
    }

    public Fetcher realFetcher() {
        return (Fetcher) other;
    }

    public Object getObject(String name) {
        return ((Fetcher) other).getObject(name);
    }
}