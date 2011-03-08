package org.stringtree.finder;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.FetcherStringHelper;

public class FetcherStringFinder extends FetcherObjectFinder implements StringFinder {
    
    public FetcherStringFinder(Fetcher fetcher) {
        super(fetcher);
    }

    public Object getObject(String name) {
        return fetcher.getObject(name);
    }

    public String get(String name) {
        return FetcherStringHelper.getString(fetcher, name);
    }
}
