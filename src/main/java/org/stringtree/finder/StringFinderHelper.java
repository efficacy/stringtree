package org.stringtree.finder;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.FallbackFetcher;

public class StringFinderHelper {

    public static StringFinder ensureFinder(Object obj) {
        if (obj instanceof StringFinder)
            return (StringFinder) obj;
        if (obj instanceof Fetcher)
            return new FetcherStringFinder((Fetcher) obj);
        return null;
    }

    public static StringKeeper ensureKeeper(StringFinder context) {
        if (context instanceof StringKeeper)
            return (StringKeeper) context;
        return new FetcherStringKeeper(context.getUnderlyingFetcher());
    }

    public static StringFinder subcontext(Fetcher fetcher, StringFinder context) {
        return new FetcherStringKeeper(new FallbackFetcher(fetcher, context
                .getUnderlyingFetcher()));
    }
}
