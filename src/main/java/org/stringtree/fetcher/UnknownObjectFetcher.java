package org.stringtree.fetcher;

import java.util.Map;

import org.stringtree.Fetcher;

public class UnknownObjectFetcher extends DelegatedFetcher {
    
    @SuppressWarnings("unchecked")
    private static Fetcher makeFetcher(Object obj, Fetcher context) {
        Fetcher ret;
        if (obj instanceof Fetcher) {
            ret = (Fetcher) obj;
        } else if (obj instanceof Map) {
            ret = new MapFetcher((Map<String, Object>) obj);
        } else if (obj instanceof String) {
            ret = new SingleStringFetcher((String) obj);
        } else if (obj != null) {
            ret = new BeanFetcher(obj, context);
        } else {
            ret = EmptyFetcher.it;
        }

        return ret;
    }

    public UnknownObjectFetcher(Object obj, Fetcher context) {
        super(makeFetcher(obj, context));
    }
}