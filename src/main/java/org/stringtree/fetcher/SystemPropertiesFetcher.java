package org.stringtree.fetcher;

import java.util.Date;

import org.stringtree.util.PropertyStreamUtils;

public class SystemPropertiesFetcher extends MapFetcher {
    
    public SystemPropertiesFetcher() {
        super(PropertyStreamUtils.convertProperties(System.getProperties()));
    }

    public Object getObject(String name) {
        if ("date".equals(name)) {
            return new Date();
        }

        return super.getObject(name);
    }

}
