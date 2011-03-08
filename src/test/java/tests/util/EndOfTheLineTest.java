package tests.util;

import org.stringtree.util.EndOfTheLine;

import junit.framework.TestCase;

public class EndOfTheLineTest extends TestCase {
    EndOfTheLine eol;
    
    public void testCR() {
        eol = EndOfTheLine.CR();
        assertFalse(eol.match('a'));
        assertFalse(eol.match('b'));
        assertFalse(eol.match('\n'));
        assertTrue(eol.match('\r'));
        assertFalse(eol.match('\n'));
        assertFalse(eol.match('c'));
        assertTrue(eol.match(-1));
    }
    
    public void testLF() {
        eol = EndOfTheLine.LF();
        assertFalse(eol.match('a'));
        assertFalse(eol.match('b'));
        assertTrue(eol.match('\n'));
        assertFalse(eol.match('\r'));
        assertTrue(eol.match('\n'));
        assertFalse(eol.match('c'));
        assertTrue(eol.match(-1));
    }
    
    public void testCRLF() {
        eol = EndOfTheLine.CRLF();
        assertFalse(eol.match('a'));
        assertFalse(eol.match('b'));
        assertFalse(eol.match('\n'));
        assertFalse(eol.match('\r'));
        assertTrue(eol.match('\n'));
        assertFalse(eol.match('c'));
        assertTrue(eol.match(-1));
    }
}
