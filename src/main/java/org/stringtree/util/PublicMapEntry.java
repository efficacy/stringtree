package org.stringtree.util;

import java.util.Map;

public class PublicMapEntry {
    
    private Map.Entry<Object,Object> entry;

    @SuppressWarnings("unchecked")
	public PublicMapEntry(Object entry) {
        this.entry = (Map.Entry<Object,Object>)entry;
    }

    public Object getKey() { 
        Object ret = entry.getKey();
        return ret;
    }

    public Object getValue() { 
        Object ret = entry.getValue();
        return ret;
    }
}
