package org.stringtree.fetcher;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.stringtree.Fetcher;
import org.stringtree.Listable;
import org.stringtree.util.ObjectFilter;

public class ListableHelper {
    
    private static final Set<String> empty = Collections.emptySet();

    @SuppressWarnings("unchecked")
    public static Listable<String> getListable(Fetcher fetcher) {
        if (fetcher instanceof Listable)
            return ((Listable) fetcher);
        return (Listable) fetcher.getObject(Listable.LIST);
    }

    public static Iterator<String> list(Fetcher fetcher) {
        Listable<String> list = getListable(fetcher);
        return list != null ? list.list() : empty.iterator();
    }

    public static Fetcher subset(Fetcher fetcher, ObjectFilter filter) {
        Iterator<String> it = list(fetcher);
        if (null == it)
            return EmptyFetcher.it;

        MapFetcher ret = new MapFetcher();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (filter.accept(key)) {
                ret.put(key, fetcher.getObject(key));
            }
        }
        ret.lock();

        return ret;
    }

    public static int count(Fetcher fetcher) {
        Iterator<String> it = list(fetcher);
        if (null == it)
            return 0;

        int ret = 0;
        while (it.hasNext()) {
            ++ret;
            it.next();
        }

        return ret;
    }
}
