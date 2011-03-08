package tests.util;

import org.stringtree.util.PrefixSequentialIdSource;

import junit.framework.TestCase;

public class PrefixSequentialIdSourceTest extends TestCase {
    PrefixSequentialIdSource ids;
    
    public void testDefaultStart() {
        ids = new PrefixSequentialIdSource("aa");
        assertEquals("aa1", ids.next());
        assertEquals("aa2", ids.next());
    }
    
    public void testExplicitStart() {
        ids = new PrefixSequentialIdSource("ping-", 91);
        assertEquals("ping-91", ids.next());
        assertEquals("ping-92", ids.next());
    }
    
    public void testNegativeStart() {
        ids = new PrefixSequentialIdSource("ping", -2);
        assertEquals("ping-2", ids.next());
        assertEquals("ping-1", ids.next());
        assertEquals("ping0", ids.next());
        assertEquals("ping1", ids.next());
    }
    
    public void testPadding() {
        ids = new PrefixSequentialIdSource("ping-", 98, 3);
        assertEquals("ping-098", ids.next());
        assertEquals("ping-099", ids.next());
        assertEquals("ping-100", ids.next());
    }
    
    public void testnegativePadding() {
        ids = new PrefixSequentialIdSource("b", -2, 3);
        assertEquals("b-02", ids.next());
        assertEquals("b-01", ids.next());
        assertEquals("b000", ids.next());
        assertEquals("b001", ids.next());
    }
    
    public void testPaddingOverflow() {
        ids = new PrefixSequentialIdSource("x", 98, 2);
        assertEquals("x98", ids.next());
        assertEquals("x99", ids.next());
        assertEquals("x100", ids.next());
    }
}
