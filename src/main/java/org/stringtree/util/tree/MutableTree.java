package org.stringtree.util.tree;

public interface MutableTree<T> extends Tree<T> {
    void setParent(Tree<T> parent);
    void addChild(Tree<T> child);
    void removeChild(Tree<T> child);
    void setValue(T value);
}
