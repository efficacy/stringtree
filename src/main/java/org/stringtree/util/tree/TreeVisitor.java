package org.stringtree.util.tree;

public interface TreeVisitor<T> {
    void enter(Tree<T> node);
    void exit(Tree<T> node);
}
