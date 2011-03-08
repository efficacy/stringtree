package org.stringtree.util.tree;

public abstract class SimpleTreeVisitor<T> implements TreeVisitor<T> {
    public void enter(Tree<T> node) {
        visit(node);
    }

    public void exit(Tree<T> node) {
        // do nothing, we are only interested in the nodes themselves
    }

    public abstract void visit(Tree<T> node);
}
