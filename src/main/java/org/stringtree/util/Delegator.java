package org.stringtree.util;

public class Delegator extends ShallowDelegator {
    
    public Delegator(Object other) {
        super(other);
    }

    public String toString() {
        return StringUtils.stringValue(other, "Deletator(null)");
    }

    public boolean equals(Object obj) {
        return other.equals(obj);
    }

    public int hashCode() {
        return other.hashCode();
    }
}
