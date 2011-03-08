package tests.util;

import org.stringtree.util.iterator.BlankPaddedSpliterator;
import org.stringtree.util.iterator.QCSVSpliterator;
import org.stringtree.util.iterator.Spliterator;

import junit.framework.TestCase;

public class StringSplitterTest extends TestCase {
    
    Spliterator splitter;

    public void testEmpty() {
        splitter = new Spliterator("");
        assertFalse(splitter.hasNext());
    }

    public void testSingle() {
        splitter = new Spliterator("hello");
        assertTrue(splitter.hasNext());
        assertEquals("hello", splitter.next());
        assertFalse(splitter.hasNext());

        splitter = new BlankPaddedSpliterator("hello");
        assertTrue(splitter.hasNext());
        assertEquals("hello", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testDouble() {
        splitter = new BlankPaddedSpliterator("hello world");
        assertTrue(splitter.hasNext());
        assertEquals("hello", splitter.nextString());
        assertTrue(splitter.hasNext());
        assertEquals("world", splitter.nextString());
        assertFalse(splitter.hasNext());
    }

    public void testDouble2() {
        splitter = new Spliterator("hello world");
        splitter.eatSeparators(false);
        assertTrue(splitter.hasNext());
        assertEquals("hello", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals(" ", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("world", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testTrailing() {
        splitter = new BlankPaddedSpliterator("hello ");
        assertTrue(splitter.hasNext());
        assertEquals("hello", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testLeading() {
        splitter = new BlankPaddedSpliterator(" hello");
        assertTrue(splitter.hasNext());
        assertEquals("hello", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testCommaSeparatedSingle() {
        splitter = new Spliterator("hello world", ",");
        assertTrue(splitter.hasNext());
        assertEquals("hello world", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testCommaSeparated() {
        splitter = new BlankPaddedSpliterator("hello,world", ",");
        assertTrue(splitter.hasNext());
        assertEquals("hello", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("world", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testCommaSeparated2() {
        splitter = new BlankPaddedSpliterator("hello, world", ",");
        assertTrue(splitter.hasNext());
        assertEquals("hello", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("world", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testCommaSeparated3() {
        splitter = new BlankPaddedSpliterator("hello , world", ",");
        assertTrue(splitter.hasNext());
        assertEquals("hello", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("world", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testCommaSeparatedEmptyField() {
        splitter = new BlankPaddedSpliterator("hello,,world", ",");
        assertTrue(splitter.hasNext());
        assertEquals("hello", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("world", splitter.next());
        assertFalse(splitter.hasNext());

        splitter = new BlankPaddedSpliterator("hello, ,world", ",");
        assertTrue(splitter.hasNext());
        assertEquals("hello", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("world", splitter.next());
        assertFalse(splitter.hasNext());

        splitter = new BlankPaddedSpliterator("hello  ,,world", ",");
        assertTrue(splitter.hasNext());
        assertEquals("hello", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("world", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testEscape() {
        splitter = new BlankPaddedSpliterator("he\\nxx");
        assertTrue(splitter.hasNext());
        assertEquals("he\nxx", splitter.next());
        assertFalse(splitter.hasNext());

        splitter = new BlankPaddedSpliterator("he\\\\xx");
        assertTrue(splitter.hasNext());
        assertEquals("he\\xx", splitter.next());
        assertFalse(splitter.hasNext());

        splitter = new BlankPaddedSpliterator("he\\,xx");
        assertTrue(splitter.hasNext());
        assertEquals("he,xx", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testQuotes() {
        splitter = new BlankPaddedSpliterator("'hello'");
        splitter.setQuotes("'");
        assertTrue(splitter.hasNext());
        assertEquals("hello", splitter.next());
        assertFalse(splitter.hasNext());
    }
    
    public void testQuotes2() {
        splitter = new BlankPaddedSpliterator(" 'ugh there' ");
        splitter.setQuotes("'");
        assertTrue(splitter.hasNext());
        assertEquals("ugh there", splitter.next());
        assertFalse(splitter.hasNext());
    }
    
    public void testQuotes3() {
        splitter = new BlankPaddedSpliterator(" 'ugh' \"don't\" ");
        splitter.setQuotes("\"");
        assertTrue(splitter.hasNext());
        assertEquals("'ugh'", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("don't", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testQCSV() {
        splitter = new QCSVSpliterator("h, 'this is' , , \"more,,stuff\"");
        assertTrue(splitter.hasNext());
        assertEquals("h", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("this is", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("more,,stuff", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testQCSVWithNoSpaces() {
        splitter = new QCSVSpliterator("a,b,");
        assertTrue(splitter.hasNext());
        assertEquals("a", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("b", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testQCSVWithEmptyFirstCell() {
        splitter = new QCSVSpliterator(",a,b");
        assertTrue(splitter.hasNext());
        assertEquals("", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("a", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("b", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testReturnQuotes() {
        splitter = new BlankPaddedSpliterator(" 'ugh there' \"zizzy\" ");
        splitter.setQuotes("'\"");
        splitter.eatQuotes(false);
        assertTrue(splitter.hasNext());
        assertEquals("'ugh there'", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("\"zizzy\"", splitter.next());
        assertFalse(splitter.hasNext());
    }

    public void testTail() {
        splitter = new BlankPaddedSpliterator("h ugh  'zaxcx");
        assertTrue(splitter.hasNext());
        assertEquals("h", splitter.next());
        assertTrue(splitter.hasNext());
        assertEquals("ugh", splitter.next());
        assertEquals("  'zaxcx", splitter.tail());
        assertFalse(splitter.hasNext());
    }
}
