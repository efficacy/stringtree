package org.stringtree.finder;

import java.util.Iterator;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.ContainerHelper;
import org.stringtree.fetcher.ListableHelper;

public class FetcherObjectFinder implements ObjectFinder {

    protected Fetcher fetcher;

    public FetcherObjectFinder(Fetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override public Object getObject(String name) {
        return fetcher.getObject(name);
    }

    public Iterator<String> list() {
        return ListableHelper.list(fetcher);
    }

    @Override public boolean contains(String name) {
        return ContainerHelper.contains(fetcher, name);
    }

    @Override public Fetcher getUnderlyingFetcher() {
        return fetcher;
    }
}
