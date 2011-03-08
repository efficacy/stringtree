package org.stringtree.fetcher;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.stringtree.Container;
import org.stringtree.Listable;
import org.stringtree.Repository;
import org.stringtree.Storer;

public class MapFetcher implements Repository, Listable<String>, Container {
    
    protected Map<String, Object> values;
    private boolean locked;

    public MapFetcher(Map<String, Object> values, boolean locked) {
        this.values = values;
        this.locked = locked;
    }

    public MapFetcher(Map<String, Object> values) {
        this(values, false);
    }

    public MapFetcher() {
        this(new HashMap<String, Object>());
    }

    public Object getObject(String key) {
        if (Storer.STORE.equals(key) || Listable.LIST.equals(key)
                || Container.CONTAINER.equals(key))
            return this;
        return values.get(key);
    }

    public void put(String key, Object value) {
        if (!locked)
            values.put(key, value);
    }

    public void putAll(Map<String, Object> map) {
        if (!locked) {
            values.putAll(map);
        }
    }

    public void remove(String name) {
        values.remove(name);
    }

    public void clear() {
        values.clear();
    }

    public void lock() {
        locked = true;
    }
    
    public boolean isLocked() {
        return locked;
    }

    public Iterator<String> list() {
        return values.keySet().iterator();
    }

    public boolean contains(String name) {
        return values.containsKey(name);
    }
    
    public Map<String, Object> getMap() {
        return values;
    }
}
