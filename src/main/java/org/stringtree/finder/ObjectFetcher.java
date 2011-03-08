package org.stringtree.finder;

import java.util.Iterator;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.ContainerHelper;
import org.stringtree.fetcher.DelegatedFetcher;
import org.stringtree.fetcher.ListableHelper;

public class ObjectFetcher extends DelegatedFetcher implements ObjectFinder {
    
    public ObjectFetcher(Fetcher fetcher) {
        super(fetcher);
    }

    public Fetcher getUnderlyingFetcher() {
        return this;
    }

    public Iterator<String> list() {
        return ListableHelper.list(realFetcher());
    }

    public boolean contains(String name) {
        return ContainerHelper.contains(realFetcher(), name);
    }
}
