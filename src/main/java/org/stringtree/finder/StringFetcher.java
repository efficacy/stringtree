package org.stringtree.finder;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.FetcherStringHelper;

public class StringFetcher extends ObjectFetcher implements StringFinder {
    
    public StringFetcher(Fetcher fetcher) {
        super(fetcher);
    }

    public String get(String name) {
        return FetcherStringHelper.getString(realFetcher(), name);
    }
}
