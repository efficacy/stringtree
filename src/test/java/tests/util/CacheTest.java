package tests.util;

import java.util.Date;

import junit.framework.TestCase;

import org.stringtree.util.AbstractCache;
import org.stringtree.util.Cached;

class TestCache extends AbstractCache<String> {
    
    public String source = "hello";

    protected boolean doLoad() {
        cache = source;
        return true;
    }

    protected void doUnload() {
        cache = null;
    }
}

public class CacheTest extends TestCase {
    
    public void testCache() {
        TestCache c = new TestCache();
        assertEquals(Cached.EMPTY, c.getCachedStatus());
        assertEquals(new Date(0), c.getTimestamp());

        assertEquals("hello", c.getValue());

        assertEquals(Cached.FULL, c.getCachedStatus());
        assertTrue(!new Date(0).equals(c.getTimestamp()));

        c.source = "ugh";
        assertEquals("hello", c.getValue());

        c.unload();

        assertEquals(Cached.EMPTY, c.getCachedStatus());
        assertEquals(new Date(0), c.getTimestamp());

        c.load();

        assertEquals(Cached.FULL, c.getCachedStatus());
        assertTrue(!new Date(0).equals(c.getTimestamp()));

        assertEquals("ugh", c.getValue());
    }
}