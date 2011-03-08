package org.stringtree.tract;

import java.util.Iterator;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.finder.FetcherStringKeeper;

public class FetcherTract extends FetcherStringKeeper implements Tract {

    public FetcherTract(Fetcher fetcher) {
        super(fetcher);
    }

    public FetcherTract() {
        super(new MapFetcher());
    }

    public void setContent(String content) {
        put(Tract.CONTENT, content);
    }

    public String getContent() {
        return get(Tract.CONTENT);
    }

    public boolean hasContent() {
        return getObject(Tract.CONTENT) != null;
    }

    public String get(String name, String dfl) {
        return contains(name) ? get(name) : dfl;
    }

    public String getCharacterSet() {
        return get(Tract.CHARSET, "UTF-8");
    }

    public String toString() {
        StringBuffer ret = new StringBuffer("{");
        Iterator<String> it = list();
        while (it.hasNext()) {
            String key = (String) it.next();
            ret.append(key);
            ret.append("=");
            ret.append(get(key));
            if (it.hasNext())
                ret.append(", ");
        }
        ret.append("}");
        return ret.toString();
    }

    public boolean equals(Object other) {
        return (other instanceof Tract) ? TractHelper.equals(this,
                (Tract) other) : false;
    }
    
    @Override
    public int hashCode() {
        return "$Fetcher Tract$".hashCode() + fetcher.hashCode();
    }
}
