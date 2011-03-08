package org.stringtree.util.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SimpleTree<T> extends EmptyTree<T> implements MutableTree<T> {
    protected Tree<T> parent;
    protected Collection<Tree<T>> children;
    protected T value;

    public SimpleTree(T value, Collection<Tree<T>> children) {
        parent = null;
        this.children = children;
        this.value = value;
    }

    public SimpleTree(T value) {
        parent = null;
        children = Collections.emptyList();
        this.value = value;
    }

    public SimpleTree() {
        this(null);
    }

    public SimpleTree(Tree<T> parent, Collection<Tree<T>> children, T value) {
        this.parent = parent;
        this.children = children;
        this.value = value;
    }

    public Tree<T> getParent() {
        return parent;
    }

    public void setParent(Tree<T> parent) {
        this.parent = parent;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Collection<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(Collection<Tree<T>> children) {
        this.children = children;
    }

    protected void ensureChildren() {
        if (children == Collections.EMPTY_LIST) {
            children = new ArrayList<Tree<T>>();
        }
    }

    public void addChild(Tree<T> child) {
        ensureChildren();
        children.add(child);
    }

    public void removeChild(Tree<T> child) {
        if (children != null) {
            children.remove(child);
        }
    }
    
    public boolean isEmpty() {
        return null == value && children.isEmpty();
    }
    
    public String toString() {
        StringBuffer ret = new StringBuffer("SimpleTree(");
        ret.append(value);
        ret.append(')');
        ret.append(children);
        return ret.toString();
    }
    
    /*
     * note - don't be tempted to add a comparison for "parent", as it recurses
     * straight back down to here again!
     */
    @SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
        if (!(obj instanceof SimpleTree)) return false;
        
        return Trees.equals(this, (SimpleTree)obj);
    }
    
    @Override
    public int hashCode() {
        return "$SimpleTree$".hashCode() + super.hashCode(); 
    }
}
