package org.stringtree.finder;

import java.util.Iterator;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.ContainerHelper;
import org.stringtree.fetcher.ListableHelper;
import org.stringtree.finder.ObjectFinder;

public class FetcherObjectFinder implements ObjectFinder {

    protected Fetcher fetcher;

    public FetcherObjectFinder(Fetcher fetcher) {
        this.fetcher = fetcher;
    }

    public Object getObject(String name) {
        return fetcher.getObject(name);
    }

    public Iterator<String> list() {
        return ListableHelper.list(fetcher);
    }

    public boolean contains(String name) {
        return ContainerHelper.contains(fetcher, name);
    }

    public Fetcher getUnderlyingFetcher() {
        return fetcher;
    }
}
