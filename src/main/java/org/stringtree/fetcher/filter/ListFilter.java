package org.stringtree.fetcher.filter;

import java.io.File;

public class ListFilter implements RepositoryFilenameFilter {
    
    private RepositoryFilenameFilter[] filters;

    public ListFilter(RepositoryFilenameFilter[] filters) {
        this.filters = filters;
    }

    public boolean accept(File dir, String name) {
        for (int i = 0; i < filters.length; ++i) {
            if (filters[i].accept(dir, name))
                return true;
        }
        return false;
    }

    public String localName(File dir, File file) {
        return filters[0].localName(dir, file);
    }

    public File fileToRead(File dir, String name) {
        for (int i = 0; i < filters.length; ++i) {
            File ret = filters[i].fileToRead(dir, name);
            if (ret != null && ret.canRead())
                return ret;
        }
        return null;
    }

    public File fileToWrite(File root, String localname) {
        return filters[0].fileToWrite(root, localname);
    }

    public boolean contains(File dir, String name) {
        for (int i = 0; i < filters.length; ++i) {
            if (filters[i].contains(dir, name))
                return true;
        }
        return false;
    }
}
