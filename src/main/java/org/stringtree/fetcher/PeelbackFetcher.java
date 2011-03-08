package org.stringtree.fetcher;

import org.stringtree.Fetcher;

public class PeelbackFetcher extends DelegatedFetcher {
    
    public PeelbackFetcher(Fetcher other) {
        super(other);
    }

    public Object getObject(String key) {
        return FetcherHelper.getPeelback(realFetcher(), key);
    }
}
