package org.stringtree.fetcher.filter;

public class AllResourcesFilter implements RepositoryResourceFilter {
    
    public static final AllResourcesFilter it = new AllResourcesFilter();

    public String fullName(String localname) {
        return localname;
    }
}
