package org.stringtree.util.tree;

import java.util.Collection;
import java.util.Iterator;

public class TreeTransformer<T,U> {
    protected Tree<T> from;
    protected MutableTree<U> to;

    public TreeTransformer(Tree<T> from, MutableTree<U> to) {
        this.from = from;
        this.to = to;
    }

    public TreeTransformer(Tree<T> from) {
        this(from, new SimpleTree<U>());
    }

    public boolean transformChildren(TreeTransformVisitor<T,U> visitor) {
        return transformChildren(visitor, from, to);
    }

    public boolean transform(TreeTransformVisitor<T,U> visitor) {
        return transformAll(visitor, from, to);
    }

    @SuppressWarnings("cast")
    protected boolean transformChildren(TreeTransformVisitor<T,U> visitor, Tree<T> from, MutableTree<U> to) {
        boolean ret = false;
        Collection<Tree<T>> fromchildren = from.getChildren();
        if (fromchildren != null) {
            Iterator<Tree<T>> it = fromchildren.iterator();
            while (it.hasNext()) {
                Tree<T> fromchild = (Tree<T>) it.next();
                MutableTree<U> tochild = new SimpleTree<U>();
                if (transformAll(visitor, fromchild, tochild)) {
                    to.addChild(tochild);
                    ret = true;
                }
            }
        }
        return ret;
    }

    protected boolean transformAll(TreeTransformVisitor<T,U> visitor, Tree<T> from, MutableTree<U> to) {
        boolean ret = visitor.enter(from, to);
        ret = transformChildren(visitor, from, to) || ret;
        ret = visitor.exit(from, to) || ret;
        return ret;
    }
}
