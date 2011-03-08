package org.stringtree.xmlevents.rss;

import java.util.HashMap;
import java.util.Map;

public class RssItem {
    private Map<String, String> attributes;
    
    public RssItem() {
        attributes = new HashMap<String, String>();
    }
    
    public void put(String key, String value) {
        attributes.put(key, value);
    }

    public String get(String key) {
        return attributes.get(key);
    }

    public String toString() {
        return attributes.toString();
    }
}
