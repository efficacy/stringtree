package org.stringtree.util;

public class ShallowDelegator {
    
    protected Object other;

    public ShallowDelegator(Object other) {
        setOther(other);
    }

    public void setOther(Object other) {
        this.other = other;
    }

    public Object getOther() {
        return other;
    }
}
