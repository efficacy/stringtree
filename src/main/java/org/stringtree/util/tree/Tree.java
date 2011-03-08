package org.stringtree.util.tree;

import java.util.Collection;

public interface Tree<T> {
    Tree<T> getParent();
    Collection<Tree<T>> getChildren();
    T getValue();
    boolean isEmpty();
}
