package tests.fetcher;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.FallbackFetcher;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.fetcher.StorerHelper;
import org.stringtree.fetcher.StringDirectoryRepository;
import org.stringtree.fetcher.filter.SuffixFilter;

public class RepositoryTest extends FetcherTestCase {
    
    private void doTest(Fetcher rep, String type) {
        StorerHelper.clear(rep);
        assertEquals("SR(" + type + ").getObject missing", null, rep
                .getObject("hello"));

        StorerHelper.put(rep, "hello", "there");
        assertEquals("SR(" + type + ").getObject present", "there", rep
                .getObject("hello"));
    }

    public void testMapStringRepository() {
        doTest(new MapFetcher(), "Map");
    }

    public void testFallbackStringRepository() {
        Map<String, Object> m = new HashMap<String, Object>();
        Fetcher f = new MapFetcher(m);
        m.put("xx", "yy");

        Fetcher r = new FallbackFetcher(new MapFetcher(), f);
        assertContains("Fallback passes through contains 1", true, r, "xx");
        assertContains("Fallback passes through contains 2", false, r, "ugh");
        assertEquals("Fallback passes through getObject 1", "yy", r
                .getObject("xx"));
        assertEquals("Fallback passes through getObject 2", null, r
                .getObject("ugh"));

        doTest(r, "Fallback");
    }

    public void testDirectoryStringRepository() {
        doTest(new StringDirectoryRepository(new File("srdir"),
                new SuffixFilter(".txt")), "Dir");
    }
}
