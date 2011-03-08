package org.stringtree.fetcher;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.stringtree.Container;
import org.stringtree.Fetcher;
import org.stringtree.Listable;
import org.stringtree.fetcher.filter.BasicRepositoryFilenameFilter;
import org.stringtree.fetcher.filter.RepositoryFilenameFilter;

public abstract class DirectoryFetcher implements Listable<String>, Container, Fetcher {
    
    protected File dir;
    protected RepositoryFilenameFilter filter;

    public DirectoryFetcher(File dir, RepositoryFilenameFilter filter) {
        this.dir = dir;
        this.filter = filter;
    }

    public DirectoryFetcher(File dir) {
        this(dir, new BasicRepositoryFilenameFilter());
    }

    public DirectoryFetcher(String dir) {
        this(new File(dir));
    }

    public static boolean contains(File dir, String name) {
        return new File(dir, name).exists();
    }

    public File fileToRead(String name) {
        if (name.startsWith("/")) name = name.substring(1);
        File ret = filter.fileToRead(dir, name);
        return ret;
    }

    public Iterator<String> list() {
        File[] files = dir.listFiles(filter);
        List<String> names = new ArrayList<String>();
        for (int i = 0; i < files.length; ++i) {
            String localName = filter.localName(dir, files[i]);
            names.add(localName);
        }

        return names.iterator();
    }

    public String toString() {
        return getClass() + "(dir='" + dir + " filter='" + filter + "'";
    }

    public boolean contains(String name) {
        return contains(dir, name);
    }

    public abstract Object getObject(String name);
}