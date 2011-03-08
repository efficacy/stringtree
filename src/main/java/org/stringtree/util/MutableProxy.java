package org.stringtree.util;

public interface MutableProxy<T> extends Proxy<T> {
    void setValue(T value);
}