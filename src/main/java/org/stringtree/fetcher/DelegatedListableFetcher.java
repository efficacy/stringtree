package org.stringtree.fetcher;

import java.util.Iterator;

import org.stringtree.Fetcher;
import org.stringtree.Listable;

public class DelegatedListableFetcher<T> extends DelegatedFetcher implements Listable<T> {
    
    public DelegatedListableFetcher(Fetcher other) {
        super(other);
    }

    protected DelegatedListableFetcher() {
        super(null);
    }

    @SuppressWarnings("unchecked")
	public Iterator<T> list() {
        return ((Listable<T>)realFetcher()).list();
    }
}
