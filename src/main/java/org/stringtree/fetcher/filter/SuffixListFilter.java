package org.stringtree.fetcher.filter;

public class SuffixListFilter extends ListFilter {
    
    public SuffixListFilter(String... suffixes) {
        super(convert(suffixes));
    }

    public static RepositoryFilenameFilter[] convert(String... suffixes) {
        RepositoryFilenameFilter[] filters = new RepositoryFilenameFilter[suffixes.length];
        for (int i = 0; i < suffixes.length; ++i) {
            filters[i] = new SuffixFilter(suffixes[i]);
        }
        return filters;
    }
}
