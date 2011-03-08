package tests.util;

import junit.framework.TestCase;

import org.stringtree.util.ArrayIterator;
import org.stringtree.util.testing.Checklist;
import org.stringtree.util.testing.ChecklistOrdered;

public class ChecklistOrderedTest extends TestCase {
    
    protected ChecklistOrdered<String> cc;

    public void setUp() {
        cc = new ChecklistOrdered<String>(Checklist.SILENT, "first", "second", "third");
    }

    public void testOrderedStaticSame() {
        assertTrue(cc.check(new ArrayIterator<String>( "first", "second", "third" )));
    }

    public void testOrderedStaticTooMany() {
        assertFalse(cc.check(new ArrayIterator<String>( "first", "second", "third", "fourth" )));
    }

    public void testOrderedStaticTooFew() {
        assertFalse(cc.check(new ArrayIterator<String>( "first", "second" )));
    }

    public void testOrderedStaticWrongOrder() {
        assertFalse(cc.check(new ArrayIterator<String>( "third", "first", "second" )));
        assertFalse(cc.check(new ArrayIterator<String>( "third", "first" )));
        assertFalse(cc.check(new ArrayIterator<String>( "third", "first", "second", "fourth" )));
        assertFalse(cc.check(new ArrayIterator<String>()));
    }

}
