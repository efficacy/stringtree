package org.stringtree.xmlevents.rss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RssChannel {

    private Map<String, String> attributes;
    private List<RssItem> items;
    
    public RssChannel() {
        attributes = new HashMap<String, String>();
        items = new ArrayList<RssItem>();
    }
    
    public void put(String key, String value) {
        attributes.put(key, value);
    }

    public String get(String key) {
        return attributes.get(key);
    }
    
    public void addItem(RssItem item) {
        items.add(item);
    }

    public List<RssItem> items() {
        return items;
    }
    
    public String toString() {
        return "RssChannel attributes=" + attributes + " items=" + items;
    }
}
