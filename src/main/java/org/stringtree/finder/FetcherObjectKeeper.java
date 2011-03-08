package org.stringtree.finder;

import org.stringtree.Fetcher;
import org.stringtree.Storer;
import org.stringtree.fetcher.StorerHelper;

public class FetcherObjectKeeper extends FetcherObjectFinder implements ObjectKeeper {

    public FetcherObjectKeeper(Fetcher fetcher) {
        super(fetcher);
    }

    public void put(String name, Object value) {
        findStorer().put(name, value);
    }

    public void remove(String name) {
        findStorer().remove(name);
    }

    public void clear() {
        findStorer().clear();
    }

    public void lock() {
        findStorer().lock();
    }
    
    public boolean isLocked() {
        return findStorer().isLocked();
    }

    private Storer findStorer() {
        return StorerHelper.find(fetcher);
    }
}
