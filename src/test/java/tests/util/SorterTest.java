package tests.util;

import java.util.Vector;

import junit.framework.TestCase;

import org.stringtree.util.sort.Sorter;

public class SorterTest extends TestCase {
    
    Sorter<String> sorter;
    Vector<String> v;

    public void setUp() {
        sorter = new Sorter<String>();
        v = new Vector<String>();
    }

    public void testEmptyVector() {
        assertTrue(v.size() == 0);
        sorter.sortVector(v);
        assertTrue(v.size() == 0);
    }

    public void testSingleItem() {
        v.addElement("aa");
        assertTrue(v.size() == 1);
        sorter.sortVector(v);
        assertTrue(v.size() == 1);
    }

    public void testPresortedUniqueStrings() {
        v.addElement("aa");
        v.addElement("bb");
        v.addElement("cc");
        assertTrue(v.size() == 3);
        sorter.sortVector(v);
        assertTrue(v.size() == 3);

        assertEquals("aa", v.elementAt(0));
        assertEquals("bb", v.elementAt(1));
        assertEquals("cc", v.elementAt(2));
    }

    public void testUnsortedUniqueStrings() {
        v.addElement("bb");
        v.addElement("aa");
        v.addElement("cc");
        assertTrue(v.size() == 3);
        sorter.sortVector(v);
        assertTrue(v.size() == 3);

        assertEquals("aa", v.elementAt(0));
        assertEquals("bb", v.elementAt(1));
        assertEquals("cc", v.elementAt(2));
    }

    public void testReversedUniqueStrings() {
        v.addElement("cc");
        v.addElement("bb");
        v.addElement("aa");
        assertTrue(v.size() == 3);
        sorter.sortVector(v);
        assertTrue(v.size() == 3);

        assertEquals("aa", v.elementAt(0));
        assertEquals("bb", v.elementAt(1));
        assertEquals("cc", v.elementAt(2));
    }

    public void testNonUniqueStrings() {
        v.addElement("bb");
        v.addElement("aa");
        v.addElement("cc");
        v.addElement("bb");
        assertTrue(v.size() == 4);
        sorter.sortVector(v);
        assertTrue(v.size() == 4);

        assertEquals("aa", v.elementAt(0));
        assertEquals("bb", v.elementAt(1));
        assertEquals("bb", v.elementAt(2));
        assertEquals("cc", v.elementAt(3));
    }
}