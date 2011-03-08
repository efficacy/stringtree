package org.stringtree.db;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class MapResultRowListener extends ResultRowListener {

    protected Map<String, Object> map;
    
    public MapResultRowListener(Map<String, Object> map) {
        this.map = map;
    }
    
    public MapResultRowListener() {
        map = new LinkedHashMap<String, Object>();
    }
    
    public void start() {
        if (null == map) map = new HashMap<String, Object>();
    }

    public Object finish() {
        return map;
    }
    
    protected void put(String key, Object value) {
        map.put(key, value);
    }
    
    protected Object get(Object key) {
        return map.get(key);
    }
}