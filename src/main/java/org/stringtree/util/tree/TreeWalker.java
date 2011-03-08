package org.stringtree.util.tree;

import java.util.Collection;
import java.util.Iterator;

public class TreeWalker<T> {
    protected Tree<T> root;

    public TreeWalker(Tree<T> root) {
        this.root = root;
    }

    public void walkChildren(TreeVisitor<T> visitor) {
        walkChildren(visitor, root);
    }

    public void walk(TreeVisitor<T> visitor) {
        walkAll(visitor, root);
    }

    @SuppressWarnings("cast")
    protected void walkChildren(TreeVisitor<T> visitor, Tree<T> node) {
        Collection<Tree<T>> children = node.getChildren();
        if (children != null) {
            Iterator<Tree<T>> it = children.iterator();
            while (it.hasNext()) {
                Tree<T> child = (Tree<T>) it.next();
                walkAll(visitor, child);
            }
        }
    }

    protected void walkAll(TreeVisitor<T> visitor, Tree<T> node) {
        visitor.enter(node);
        walkChildren(visitor, node);
        visitor.exit(node);
    }
}
