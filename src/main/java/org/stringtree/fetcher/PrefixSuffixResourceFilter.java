package org.stringtree.fetcher;

import org.stringtree.fetcher.filter.RepositoryResourceFilter;

public class PrefixSuffixResourceFilter implements RepositoryResourceFilter {
    
    private String prefix;
    private String suffix;

    public PrefixSuffixResourceFilter(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String fullName(String localname) {
        return prefix + localname + suffix;
    }
}
