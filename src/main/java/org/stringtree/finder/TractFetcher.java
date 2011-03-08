package org.stringtree.finder;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.fetcher.FetcherTractHelper;

public class TractFetcher extends ObjectFetcher implements TractFinder {
    
    public TractFetcher(Fetcher fetcher) {
        super(fetcher);
    }

    public Tract get(String name) {
        return FetcherTractHelper.getTract(realFetcher(), name);
    }
}
