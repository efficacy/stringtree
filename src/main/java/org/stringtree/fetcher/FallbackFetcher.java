package org.stringtree.fetcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.stringtree.Fetcher;

public class FallbackFetcher implements Fetcher {
    
    private List<Fetcher> fetchers;

    public FallbackFetcher(Fetcher[] fetchers) {
        this.fetchers = Arrays.asList(fetchers);
    }

    public FallbackFetcher(List<Fetcher> fetchers) {
        this.fetchers = fetchers;
    }

    public FallbackFetcher() {
        this.fetchers = new ArrayList<Fetcher>();
    }

    public FallbackFetcher(Fetcher top, Fetcher context) {
        this(new Fetcher[] { top, context });
    }

    public static Object getObject(String name, List<Fetcher> fetchers) {
        Object ret = null;

        Iterator<Fetcher> it = fetchers.iterator();
        while (ret == null && it.hasNext()) {
            Fetcher fetcher = (Fetcher)it.next();
            ret = fetcher.getObject(name);
        }

        return ret;
    }

    public Object getObject(String name) {
        return getObject(name, fetchers);
    }

    public String toString() {
        StringBuffer ret = new StringBuffer(getClass() + ": [");
        Iterator<Fetcher> it = fetchers.iterator();
        while (it.hasNext()) {
            ret.append(" ");
            ret.append(it.next());
        }
        ret.append(" ]");

        return ret.toString();
    }

    public Fetcher top() {
        return fetchers.get(0);
    }

    public Iterator<Fetcher> listFetchers() {
        return fetchers.iterator();
    }
    
    public void add(Object obj) {
        if (obj instanceof Fetcher) {
            fetchers.add((Fetcher)obj);
        } else {
            System.err.println("attempt to add object " + obj + " to fallback fetcher");
        }
    }
}