package org.stringtree.fetcher;

import org.stringtree.Fetcher;
import org.stringtree.Repository;
import org.stringtree.Storer;
import org.stringtree.util.StringUtils;

public class RepositoryHelper {
    
    public static Repository ensureRepository(Fetcher fetcher) {
        if (fetcher instanceof Repository)
            return (Repository) fetcher;
        return new FetcherRepository(fetcher);
    }

    public static Object getObject(Storer storer, String key) {
        return (storer instanceof Fetcher) ? ((Fetcher) storer).getObject(key)
                : null;
    }

    public static String getString(Storer storer, String key) {
        return StringUtils.nullToEmpty(getObject(storer, key));
    }
}
