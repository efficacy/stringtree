package org.stringtree.util.tree;

import java.util.Collection;

import org.stringtree.util.Cached;

public class CachedTree<T> extends ProxyTree<T> {
    public CachedTree() {
        super();
    }

    public CachedTree(Tree<T> parent, Collection<Tree<T>> children, T value) {
        super(parent, children, value);
    }

    @SuppressWarnings("unchecked")
	public int getCachedStatus() {
        int ret = Cached.FULL;
        if (value != null && value instanceof Cached) {
            ret = ((Cached) value).getCachedStatus();
        }

        return ret;
    }
}
