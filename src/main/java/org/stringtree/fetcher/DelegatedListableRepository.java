package org.stringtree.fetcher;

import java.util.Iterator;

import org.stringtree.Listable;
import org.stringtree.Repository;

public class DelegatedListableRepository<T> extends DelegatedRepository implements Listable<T> {

    public DelegatedListableRepository(Repository other) {
        super(other);
    }

    protected DelegatedListableRepository() {
        super(null);
    }
    
    @SuppressWarnings("unchecked")
	public Iterator<T> list() {
        return ((Listable<T>)realFetcher()).list();
    }
}
