package tests.util;

import junit.framework.TestCase;

import org.stringtree.Tract;
import org.stringtree.tract.EmptyTract;
import org.stringtree.tract.MapTract;

public class TractTest extends TestCase {
    
    private Tract t;
    private Tract t2;

    public void testEmpty() {
        t = EmptyTract.it;
        assertEquals("Empty getContent", "", t.getContent());
        assertEquals("Empty hasAttribute", false, t.contains("ugh"));
        assertEquals("Empty getAttribute", "", t.get("ugh"));

        assertEquals("Empty toString", "{}", t.toString());
        assertEquals("Empty equals", t, new MapTract());
    }

    public void testString() {
        t = new MapTract("hello");
        assertEquals("String getContent", "hello", t.getContent());
        assertEquals("String hasAttribute", false, t.contains("ugh"));
        assertEquals("String getAttribute", "", t.get("ugh"));
        assertEquals("String getAttribute", null, t.getObject("ugh"));

        assertEquals("String toString", "{CONTENT=hello}", t.toString());
    }

    public void testAttributesAndContent() {
        t = new MapTract("hello");
        t.put("hoop", "ball");

        assertEquals("Both getContent", "hello", t.getContent());
        assertEquals("Both hasAttribute", false, t.contains("ugh"));
        assertEquals("Both getAttribute", "", t.get("ugh"));
        assertEquals("Both getAttribute", null, t.getObject("ugh"));
        assertEquals("Both hasAttribute", true, t.contains("hoop"));
        assertEquals("Both getAttribute", "ball", t.get("hoop"));

        assertEquals("Both toString", "{CONTENT=hello, hoop=ball}", t
                .toString());

        t2 = new MapTract("hello");
        t2.put("hoop", "ball");
        assertEquals("Both equals", t2, t);

        Object obj = t2;
        assertEquals("Both equals", t, obj);
    }

    public void testEquals() {
        t = new MapTract("hello");
        assertTrue("Tract equals 1", t.equals(new MapTract("hello")));

        t2 = new MapTract("hello");
        assertTrue("Tract equals 2", t.equals(t2));

        t2.put("a", "b");
        assertFalse("Tract equals 3", t.equals(t2));

        t.put("a", "b");
        assertTrue("Tract equals 4", t.equals(t2));

        t.put("d", "e");
        assertFalse("Tract equals 5", t.equals(t2));

        t2.put("d", "f");
        assertFalse("Tract equals 6", t.equals(t2));

        t2.put("d", "e");
        assertTrue("Tract equals 7", t.equals(t2));

        t2.setContent("goodbye");
        assertFalse("Tract equals 8", t.equals(t2));
    }

}