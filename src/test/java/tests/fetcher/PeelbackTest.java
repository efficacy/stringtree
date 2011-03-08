package tests.fetcher;

import org.stringtree.fetcher.FetcherHelper;
import org.stringtree.fetcher.MapFetcher;

import junit.framework.TestCase;

public class PeelbackTest extends TestCase {
    
    MapFetcher context;
    SimpleBean ugh = new SimpleBean();

    public void setUp() {
        context = new MapFetcher();
        context.put("x1", "goodbye");
        context.put("x2", ugh);
        context.put("x3.aa", "whatever");
    }

    public void testSimpleText() {
        assertEquals("goodbye", FetcherHelper.getPeelback(context, "x1"));
    }

    public void testSimpleObject() {
        assertEquals(ugh, FetcherHelper.getPeelback(context, "x2"));
    }

    public void testBeanMethod() {
        assertEquals("hello from aa", FetcherHelper.getPeelback(context,
                "x2.aa"));
    }

    public void testRegularMethod() {
        assertEquals("hello from bb", FetcherHelper.getPeelback(context,
                "x2.bb"));
    }

    public void testPublicField() {
        assertEquals("hello from cc", FetcherHelper.getPeelback(context,
                "x2.cc"));
    }
}
