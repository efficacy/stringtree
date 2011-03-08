package org.stringtree.util.tree;

import java.util.Collection;
import java.util.Collections;

public class EmptyTree<T> implements Tree<T> {
    public Tree<T> getParent() {
        return null;
    }

    public T getValue() {
        return null;
    }

    public Collection<Tree<T>> getChildren() {
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
	public boolean equals(Object other) {
        return (other instanceof Tree) ? Trees.equals(this, (Tree)other) : false;
    }

    public boolean isEmpty() {
        return true;
    }
    
    @Override
    public int hashCode() {
        return "$Empty Tree$".hashCode();
    }
}
