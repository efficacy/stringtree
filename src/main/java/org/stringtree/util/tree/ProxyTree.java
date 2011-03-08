package org.stringtree.util.tree;

import java.util.Collection;

import org.stringtree.util.MutableProxy;
import org.stringtree.util.Proxy;

public class ProxyTree<T> extends SimpleTree<T> {
    public ProxyTree() {
        super();
    }

    public ProxyTree(Tree<T> parent, Collection<Tree<T>> children, T value) {
        super(parent, children, value);
    }

    @SuppressWarnings("unchecked")
    public T getValue() {
        T ret = value;
        if (ret != null && ret instanceof Proxy) {
            ret = ((Proxy<T>) ret).getValue();
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    public void setValue(T value) {
        if (this.value != null && this.value instanceof Proxy) {
            if (this.value instanceof MutableProxy) {
                ((MutableProxy<T>) this.value).setValue(value);
            } else {
                throw new UnsupportedOperationException(
                        "This proxy object is read-only");
            }
        } else {
            this.value = value;
        }
    }
}
