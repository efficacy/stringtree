package org.stringtree.db;

import java.util.ArrayList;
import java.util.List;

public abstract class CollectingResultRowListener<T> extends ResultRowListener {
    protected List<T> collection;
    
    public CollectingResultRowListener(List<T> collection) {
        this.collection = collection;
    }
    
    
    public CollectingResultRowListener() {
        this(new ArrayList<T>());
    }
    
    @Override
    public void start() {
        // nothing unless overridden
    }

    @Override
    public Object finish() {
        return collection;
    }
    
    protected void add(T object) {
        collection.add(object);
    }

}
