package tests.util;

import java.util.Arrays;
import java.util.Collections;

import junit.framework.TestCase;

import org.stringtree.util.ArrayIterator;
import org.stringtree.util.testing.Checklist;

public class ChecklistTest extends TestCase {
    
    protected Checklist<String> cc;

    public void setUp() {
        cc = new Checklist<String>(Checklist.SILENT, "first", "second", null );
    }

    public void testEmpty() {
        assertTrue(!cc.isChecked("first"));
        assertTrue(!cc.isChecked("second"));
        assertTrue(!cc.isChecked(null));
        assertTrue(!cc.allCheckedAtLeastOnce());
        assertTrue(!cc.anyCheckedMoreThanOnce());
        assertTrue(!cc.anyUnknownItemsChecked());
        assertTrue(!cc.allAndOnlyOnce());

        assertTrue(!cc.isChecked("unknown"));
    }

    public void testIsChecked() {
        cc.tick("first");
        assertTrue(cc.isChecked("first"));
        assertTrue(!cc.isChecked("second"));
        assertTrue(!cc.isChecked(null));
        assertTrue(!cc.isChecked("unknown"));
    }

    public void testAllCheckedAtLeastOnce() {
        cc.tick("first");
        assertTrue(!cc.allCheckedAtLeastOnce());
        cc.tick("second");
        assertTrue(!cc.allCheckedAtLeastOnce());
        cc.tick((String)null);
        assertTrue(cc.allCheckedAtLeastOnce());
        cc.tick("unknown");
        assertTrue(cc.allCheckedAtLeastOnce());
    }

    public void testAnyCheckedMoreThanOnce() {
        cc.tick("first");
        assertTrue(!cc.anyCheckedMoreThanOnce());
        cc.tick("first");
        assertTrue(cc.anyCheckedMoreThanOnce());
    }

    public void testUnknownCheckedMoreThanOnce() {
        cc.tick("unknown");
        assertTrue(!cc.anyCheckedMoreThanOnce());
        cc.tick("unknown");
        assertTrue(!cc.anyCheckedMoreThanOnce());
    }

    public void testAnyUnknownItemsChecked() {
        cc.tick("first");
        assertTrue(!cc.anyUnknownItemsChecked());
        cc.tick("unknown");
        assertTrue(cc.anyUnknownItemsChecked());
    }

    public void testAllAndOnlyOnce() {
        cc.tick("first");
        assertTrue(!cc.allAndOnlyOnce());
        cc.tick("second");
        assertTrue(!cc.allAndOnlyOnce());
        cc.tick((String)null);
        assertTrue(cc.allAndOnlyOnce());
        cc.tick("unknown");
        assertTrue(!cc.allAndOnlyOnce());
    }

    public void testIterator() {
        cc = new Checklist<String>(Checklist.SILENT,  "first", "second", "third" );

        cc.consider(new ArrayIterator<String>("third", "first", "second"));
        assertTrue(cc.allAndOnlyOnce());

        cc.consider(new ArrayIterator<String>("third", "first"));
        assertTrue(!cc.allAndOnlyOnce());

        cc.consider(new ArrayIterator<String>("third", "first", "second", "fourth"));
        assertTrue(!cc.allAndOnlyOnce());

        cc.consider(new ArrayIterator<String>());
        assertTrue(!cc.allAndOnlyOnce());
    }

    public void testOverall() {
        Checklist<String> cc = new Checklist<String>(Checklist.SILENT,  "first", "second", "third" );
        
        assertTrue(cc.check(new ArrayIterator<String>( "third", "first", "second" )));
        assertFalse(cc.check(new ArrayIterator<String>( "third", "first" )));
        assertFalse(cc.check(new ArrayIterator<String>( "third", "first", "second", "fourth" )));
        assertFalse(cc.check(new ArrayIterator<String>()));
    }
    
    public void testSubset() {
        assertTrue(Checklist.compareSubset(0, new String[] {}, Collections.EMPTY_LIST));
        assertFalse(Checklist.compareSubset(1, new String[] { "a" }, Collections.EMPTY_LIST));
        assertTrue(Checklist.compareSubset(0, new String[] { "a" }, Collections.EMPTY_LIST));
        assertTrue(Checklist.compareSubset(1, new String[] { "a", "b" }, Arrays.asList(new String[] { "a" })));
        assertFalse(Checklist.compareSubset(1, new String[] { "a" }, Arrays.asList(new String[] { "b" })));
        assertFalse(Checklist.compareSubset(1, new String[] { "a" }, Arrays.asList(new String[] { "a", "b" })));
        assertTrue(Checklist.compareSubset(2, new String[] { "a", "b" }, Arrays.asList(new String[] { "a", "b" })));
        assertTrue(Checklist.compareSubset(2, new String[] { "a", "b" }, Arrays.asList(new String[] { "b", "a" })));
        assertFalse(Checklist.compareSubset(2, new String[] { "a", "b" }, Arrays.asList(new String[] { "b" })));
        assertFalse(Checklist.compareSubset(2, new String[] { "a", "b" }, Arrays.asList(new String[] { "b", "b" })));
    }
}