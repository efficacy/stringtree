package tests.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.stringtree.util.testing.Checklist;
import org.stringtree.util.tree.MutableTree;
import org.stringtree.util.tree.SimpleTree;
import org.stringtree.util.tree.SimpleTreeVisitor;
import org.stringtree.util.tree.Tree;
import org.stringtree.util.tree.TreeVisitor;
import org.stringtree.util.tree.TreeWalker;

class ListingVisitor<T> extends ArrayList<Object> implements TreeVisitor<T> {
    public void visit(Tree<T> node) {
        add(node);
    }

    @SuppressWarnings("unchecked")
    public Tree<T> getNode(int n) {
        return (Tree<T>) get(n);
    }

    @Override public void enter(Tree<T> node) {
        add("enter: " + node.getValue());
        visit(node);
    }

    @Override public void exit(Tree<T> node) {
        add("exit");
    }
}

class SimpleVisitor<T> extends SimpleTreeVisitor<T> {
    List<String> list = new ArrayList<String>();

    @Override public void visit(Tree<T> node) {
        list.add("visit: " + node.getValue());
    }

    public int size() {
        return list.size();
    }

    public String get(int index) {
        return list.get(index);
    }
}

public class TreeWalkerTest extends TestCase {

    MutableTree<String> t;
    TreeWalker<String> tw;
    ListingVisitor<String> visitor;
    SimpleVisitor<String> simple;

    @Override public void setUp() {
        t = new SimpleTree<String>();
        t.setValue("root");

        tw = new TreeWalker<String>(t);
        visitor = new ListingVisitor<String>();
        simple = new SimpleVisitor<String>();

        assertEquals(0, visitor.size());
        assertEquals(0, simple.size());
    }


    public void testEmptyTree() {
        tw.walk(visitor);
        assertEquals(3, visitor.size());
        assertEquals("enter: root", visitor.get(0));
        assertEquals("root", visitor.getNode(1).getValue());
        assertTrue(visitor.getNode(1).getChildren().isEmpty());

        assertEquals("exit", visitor.get(2));

        tw.walk(simple);
        assertEquals(1, simple.size());
        assertEquals("visit: root", simple.get(0));
    }

    public void testNodeWithChild() {
        Tree<String> c1 = new SimpleTree<String>(t, TreeTest.empty, "c1");
        t.addChild(c1);

        tw.walk(visitor);

        assertEquals(6, visitor.size());
        assertEquals("enter: root", visitor.get(0));
        assertEquals("root", visitor.getNode(1).getValue());
        assertEquals("enter: c1", visitor.get(2));
        assertEquals("c1", visitor.getNode(3).getValue());
        assertEquals("exit", visitor.get(4));
        assertEquals("exit", visitor.get(5));

        assertFalse(visitor.getNode(1).getChildren().isEmpty());
        assertTrue(new Checklist<Tree<String>>( c1 ).check(
            visitor.getNode(1).getChildren()));

        tw.walk(simple);
        assertEquals(2, simple.size());
        assertEquals("visit: root", simple.get(0));
        assertEquals("visit: c1", simple.get(1));
    }

    public void testNodeWithChildren() {
        Tree<String> c1 = new SimpleTree<String>(t, TreeTest.empty, "c1");
        Tree<String> c2 = new SimpleTree<String>(t, TreeTest.empty, "c2");
        t.addChild(c1);
        t.addChild(c2);

        tw.walk(visitor);

        assertEquals(9, visitor.size());
        assertEquals("enter: root", visitor.get(0));
        assertEquals("root", visitor.getNode(1).getValue());
        assertEquals("enter: c1", visitor.get(2));
        assertEquals("c1", visitor.getNode(3).getValue());
        assertEquals("exit", visitor.get(4));
        assertEquals("enter: c2", visitor.get(5));
        assertEquals("c2", visitor.getNode(6).getValue());
        assertEquals("exit", visitor.get(7));
        assertEquals("exit", visitor.get(8));

        assertTrue(new Checklist<Tree<String>>( c1, c2 ).check(
                visitor.getNode(1).getChildren()));

        tw.walk(simple);
        assertEquals(3, simple.size());
        assertEquals("visit: root", simple.get(0));
        assertEquals("visit: c1", simple.get(1));
        assertEquals("visit: c2", simple.get(2));
    }

    public void testRootless() {
        Tree<String> c1 = new SimpleTree<String>(t, TreeTest.empty, "c1");
        Tree<String> c2 = new SimpleTree<String>(t, TreeTest.empty, "c2");
        t.addChild(c1);
        t.addChild(c2);

        tw.walkChildren(visitor);

        assertEquals(6, visitor.size());
        assertEquals("enter: c1", visitor.get(0));
        assertEquals("c1", visitor.getNode(1).getValue());
        assertEquals("exit", visitor.get(2));
        assertEquals("enter: c2", visitor.get(3));
        assertEquals("c2", visitor.getNode(4).getValue());
        assertEquals("exit", visitor.get(5));

        tw.walkChildren(simple);

        assertEquals(2, simple.size());
        assertEquals("visit: c1", simple.get(0));
        assertEquals("visit: c2", simple.get(1));
    }
}