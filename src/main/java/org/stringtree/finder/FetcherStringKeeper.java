package org.stringtree.finder;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.FetcherStringHelper;

public class FetcherStringKeeper extends FetcherObjectKeeper implements StringKeeper {

    public FetcherStringKeeper(Fetcher fetcher) {
        super(fetcher);
    }

    public String get(String name) {
        return FetcherStringHelper.getString(fetcher, name);
    }
}
