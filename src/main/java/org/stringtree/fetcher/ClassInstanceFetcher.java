package org.stringtree.fetcher;

import org.stringtree.Fetcher;
import org.stringtree.finder.StringFinder;
import org.stringtree.util.ContextClassUtils;

public class ClassInstanceFetcher implements Fetcher {
    
    protected StringFinder context;

    public ClassInstanceFetcher(StringFinder context) {
        this.context = context;
    }

    public Object getObject(String name) {
        return ContextClassUtils.createObject(name, context, true);
    }
}