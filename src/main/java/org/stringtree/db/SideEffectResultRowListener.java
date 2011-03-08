package org.stringtree.db;

public abstract class SideEffectResultRowListener<T> extends ResultRowListener {
    
    protected T target;
    
    public SideEffectResultRowListener(T target) {
        this.target = target;
    }
}
