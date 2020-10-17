package tests.util;

import java.util.Collection;
import java.util.Collections;

import junit.framework.TestCase;

import org.stringtree.util.testing.Checklist;
import org.stringtree.util.tree.EmptyTree;
import org.stringtree.util.tree.MutableTree;
import org.stringtree.util.tree.SimpleTree;
import org.stringtree.util.tree.Tree;
import org.stringtree.util.tree.Trees;

public class TreeTest extends TestCase {
    public static final Collection<Tree<String>> empty = Collections.emptyList();

    public void testEmptyTree() {
        Tree<Object> t = new EmptyTree<Object>();
        assertEquals(null, t.getParent());
        assertTrue(t.getChildren().isEmpty());
        assertEquals(null, t.getValue());

        assertEquals(new EmptyTree<Object>(), t);
        assertEquals(Trees.EMPTY_TREE, t);
        assertEquals(Trees.EMPTY_TREE, new EmptyTree<Object>());
    }

    public void testEmptySimpleTree() {
        Tree<String> t = new SimpleTree<String>();

        assertEquals(null, t.getParent());
        assertTrue(t.getChildren().isEmpty());
        assertEquals(null, t.getValue());

        assertEquals(Trees.EMPTY_TREE, t);
        assertEquals(new SimpleTree<String>(null, empty, null), t);
    }

    public void testNodeWithValue() {
        MutableTree<String> t = new SimpleTree<String>();
        t.setValue("hoople");

        assertEquals(null, t.getParent());
        assertTrue(t.getChildren().isEmpty());
        assertEquals("hoople", t.getValue());

        assertEquals(new SimpleTree<String>(null, empty, "hoople"), t);
    }

    public void testNodeWithParent() {
        MutableTree<String> t = new SimpleTree<String>();
        Tree<String> parent = new EmptyTree<String>();
        t.setParent(parent);

        assertEquals(parent, t.getParent());
        assertTrue(t.getChildren().isEmpty());
        assertEquals(null, t.getValue());

        assertEquals(new SimpleTree<String>(parent, empty, null), t);
    }

    public void testNodeWithChild() {
        MutableTree<String> t = new SimpleTree<String>();
        Tree<String> c1 = new SimpleTree<String>(t, empty, "c1");
        t.addChild(c1);

        assertEquals(null, t.getParent());
        assertFalse(t.getChildren().isEmpty());
        assertTrue(new Checklist<Tree<String>>( c1 ).check(t.getChildren()));
        assertEquals(null, t.getValue());

        MutableTree<String> t2 = new SimpleTree<String>();
        t2.addChild(new SimpleTree<String>(t, empty, "c1"));
        assertEquals(t2, t);
    }

    public void testNodeWithChildren() {
        MutableTree<String> t = new SimpleTree<String>();
        Tree<String> c1 = new SimpleTree<String>(t, empty, "c1");
        Tree<String> c2 = new SimpleTree<String>(t, empty, "c2");
        t.addChild(c1);
        t.addChild(c2);

        assertEquals(null, t.getParent());
        assertEquals(null, t.getValue());
        assertFalse(t.getChildren().isEmpty());

        assertTrue(new Checklist<Tree<String>>( c1, c2 ).check(t.getChildren()));

        MutableTree<String> t2 = new SimpleTree<String>();
        t2.addChild(new SimpleTree<String>(t2, empty, "c1"));
        t2.addChild(new SimpleTree<String>(t2, empty, "c2"));
        assertEquals(t2, t);

        t.removeChild(c2);

        assertTrue(new Checklist<Tree<String>>( c1 ).check(t.getChildren()));

        MutableTree<String> t3 = new SimpleTree<String>();
        t3.addChild(new SimpleTree<String>(t3, empty, "c1"));
        assertEquals(t3, t);
    }
}