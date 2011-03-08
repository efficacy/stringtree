package org.stringtree.fetcher;

import org.stringtree.Container;
import org.stringtree.util.PropertyResourceUtils;

public class ResourcePropertiesFetcher extends DelegatedFetcher implements Container {
    
    public ResourcePropertiesFetcher(Object self, String name) {
        super(new MapFetcher(PropertyResourceUtils.readPropertyResource(self,
                name)));
    }

    public boolean contains(String name) {
        return ((Container) getOther()).contains(name);
    }
}