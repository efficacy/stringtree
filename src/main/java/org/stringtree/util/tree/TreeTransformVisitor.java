package org.stringtree.util.tree;

public interface TreeTransformVisitor<T, U> {
    boolean enter(Tree<T> from, MutableTree<U> to);
    boolean exit(Tree<T> from, MutableTree<U> to);
}
