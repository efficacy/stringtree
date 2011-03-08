package org.stringtree.fetcher;

import org.stringtree.Fetcher;
import org.stringtree.Repository;
import org.stringtree.Storer;

public class FallbackRepository extends FallbackFetcher implements Repository {
    public FallbackRepository(Fetcher top, Fetcher context) {
        super(top, context);
    }

    private Storer findStorer() {
        return (Storer) getObject(Storer.STORE);
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
}
