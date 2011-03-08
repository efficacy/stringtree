package org.stringtree.fetcher.filter;

import java.io.File;

public class SuffixFilter extends BasicRepositoryFilenameFilter {
    
    private String suffix;

    public SuffixFilter(String suffix) {
        this.suffix = suffix;
    }

    public boolean accept(File dir, String name) {
        return name.endsWith(suffix);
    }

    protected String externalName(String name) {
        return name + suffix;
    }

    protected String internalName(String name) {
        if (name.endsWith(suffix)) {
            name = name.substring(0, name.length() - suffix.length());
        }
        return name;
    }

    public String localName(File dir, File file) {
        String name = leafName(dir, file);
        String ret = internalName(name);
        return ret;
    }
    
    public String toString() {
        return "SuffixFilter[" + suffix + "]"; 
    }
}
