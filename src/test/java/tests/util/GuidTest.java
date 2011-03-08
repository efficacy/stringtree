package tests.util;

import java.util.HashSet;
import java.util.Set;

import org.stringtree.util.GuidGenerator;

import junit.framework.TestCase;

public class GuidTest extends TestCase {
    public void testSingleGuid() {
        long before = System.currentTimeMillis();
        String guid = GuidGenerator.create();
        long after = System.currentTimeMillis();
//System.err.println("generated guid [" + guid + "], took " + (after-before) + "ms");
        assertTrue(after-before < 2000);
        assertEquals(40, guid.length());
    }

    public void testMultipleGuid() {
        String[] guids = new String[5];
        long before = System.currentTimeMillis();
        guids[0] = GuidGenerator.create();
        guids[1] = GuidGenerator.create();
        guids[2] = GuidGenerator.create();
        guids[3] = GuidGenerator.create();
        guids[4] = GuidGenerator.create();
        long after = System.currentTimeMillis();
//System.err.println("generated 5 guids, took " + (after-before) + "ms");
        assertTrue(after-before < 2000);
    }
    
    public void testForClashes() {
        int n = 10000;
        Set<String> set = new HashSet<String>();
        long before = System.currentTimeMillis();
        for (int i = 0; i < n; ++i) {
            String guid = GuidGenerator.create();
            if (set.contains(guid)) fail("duplicate guid!");
            set.add(guid);
        }
        long after = System.currentTimeMillis();
//System.err.println("generated " + n + " guids, took " + (after-before) + "ms");
        assertTrue(after-before < 10000);
    }
}
