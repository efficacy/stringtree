package org.stringtree.fetcher.filter;

import org.stringtree.util.ObjectFilter;

public class StringPrefixFilter implements ObjectFilter {
    
    private String prefix;

    public StringPrefixFilter(String prefix) {
        this.prefix = prefix;
    }

    public boolean accept(Object obj) {
        return ((String) obj).startsWith(prefix);
    }
}