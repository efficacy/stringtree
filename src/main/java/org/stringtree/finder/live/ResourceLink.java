package org.stringtree.finder.live;

import org.stringtree.Fetcher;
import org.stringtree.SystemContext;
import org.stringtree.fetcher.ResourceFetcher;
import org.stringtree.finder.StringFinder;
import org.stringtree.util.StringUtils;

public class ResourceLink extends NamedLink {
    
    protected StringFinder context;

    public ResourceLink(String name) {
        super(name);
        this.context = null;
    }

    public void init(StringFinder context) {
        this.context = context;
    }

    public Object getObject() {
        ClassLoader loader = null;
        Object cl = null;
        if (context != null) cl = context.getObject(SystemContext.SYSTEM_CLASSLOADER);
        if (cl instanceof ClassLoader) loader = (ClassLoader) cl;
        if (loader == null) loader = getClass().getClassLoader();
        Fetcher fetcher = new ResourceFetcher(loader);
        Object ret = fetcher.getObject(name);
        return ret;
    }

    public String toString() {
        return StringUtils.stringValue(getObject());
    }
}
