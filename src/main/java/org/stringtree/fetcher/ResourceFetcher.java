package org.stringtree.fetcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;

import org.stringtree.Fetcher;
import org.stringtree.Listable;
import org.stringtree.fetcher.filter.AllResourcesFilter;
import org.stringtree.fetcher.filter.RepositoryResourceFilter;
import org.stringtree.util.enumeration.EmptyEnumeration;
import org.stringtree.util.iterator.EnumerationIterator;

@SuppressWarnings("rawtypes")
public class ResourceFetcher implements Fetcher, Listable {
    
    private ClassLoader loader;
    private RepositoryResourceFilter filter;

    public ResourceFetcher(Object context, RepositoryResourceFilter filter) {
        if (context instanceof ClassLoader) {
            loader = (ClassLoader) context;
        } else if (context instanceof Class) {
            loader = ((Class) context).getClassLoader();
        } else {
            loader = context.getClass().getClassLoader();
        }

        this.filter = filter;
    }

    public ResourceFetcher(RepositoryResourceFilter filter) {
        this(Thread.currentThread().getContextClassLoader(), filter);
    }

    public ResourceFetcher(Object context) {
        this(context, AllResourcesFilter.it);
    }

    public ResourceFetcher() {
        this(AllResourcesFilter.it);
    }

    public Object getObject(String name) {
        String ret = null;
        try {
            String fullName = filter.fullName(name);
            InputStream in = loader.getResourceAsStream(fullName);
            if (in != null) {
                StringBuffer buf = new StringBuffer();

                try {
                    for (int c = in.read(); c != -1; c = in.read()) {
                        buf.append((char) c);
                    }
                } finally {
                    in.close();
                }

                ret = buf.toString();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
	public Iterator list() {
        Enumeration en = new EmptyEnumeration();
        try {
            en = loader.getResources("com/io_content/services/exposed/xsl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new EnumerationIterator(en);
    }
}
