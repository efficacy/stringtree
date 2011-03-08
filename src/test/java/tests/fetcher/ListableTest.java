package tests.fetcher;

import junit.framework.TestCase;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.EmptyFetcher;
import org.stringtree.fetcher.FallbackFetcher;
import org.stringtree.fetcher.ListableHelper;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.fetcher.StorerHelper;
import org.stringtree.util.ObjectFilter;
import org.stringtree.util.testing.Checklist;

public class ListableTest extends TestCase {
    
    Fetcher fetcher;

    public void testEmpty() {
        fetcher = new EmptyFetcher();
        assertTrue(new Checklist<String>().check(ListableHelper
                .list(fetcher)));
    }

    public void testSingle() {
        fetcher = new MapFetcher();
        StorerHelper.put(fetcher, "ugh", "hello");
        assertTrue(new Checklist<String>( "ugh" ).check(
                ListableHelper.list(fetcher)));
    }

    public void testDouble() {
        fetcher = new MapFetcher();
        StorerHelper.put(fetcher, "ugh", "hello");
        StorerHelper.put(fetcher, "zot", "wibble");
        assertTrue(new Checklist<String>( "ugh", "zot" ).check(
                ListableHelper.list(fetcher)));
    }

    public void testSelect() {
        fetcher = new MapFetcher();
        StorerHelper.put(fetcher, "ugh", "hello");
        StorerHelper.put(fetcher, "zot", "wibble");
        Fetcher subset = ListableHelper.subset(fetcher, new ObjectFilter() {
            public boolean accept(Object obj) {
                return ((String) obj).startsWith("u");
            }
        });
        assertEquals("hello", fetcher.getObject("ugh"));
        assertEquals("wibble", fetcher.getObject("zot"));

        assertEquals("hello", subset.getObject("ugh"));
        assertEquals("", subset.getObject("zot"));

        assertTrue(new Checklist<String>( "ugh" ).check(
                ListableHelper.list(subset)));
    }

    public void testFallback() {
        MapFetcher r1 = new MapFetcher();
        MapFetcher r2 = new MapFetcher();
        fetcher = new FallbackFetcher(r1, r2);

        StorerHelper.put(r1, "ugh", "hello");
        StorerHelper.put(r1, "zot", "wibble");
        StorerHelper.put(r2, "huh", "wwhat");
        assertTrue(new Checklist<String>( "ugh", "zot", "huh" ).check(
                ListableHelper.list(fetcher)));
    }

    public void testFallback2() {
        MapFetcher r1 = new MapFetcher();
        MapFetcher r2 = new MapFetcher();
        fetcher = new FallbackFetcher(r1, r2);

        StorerHelper.put(r1, "ugh", "hello");
        StorerHelper.put(r1, "zot", "wibble");
        StorerHelper.put(r1, "huh", "wwhat");
        assertTrue(new Checklist<String>( "ugh", "zot", "huh" ).check(
                ListableHelper.list(fetcher)));
    }
}
