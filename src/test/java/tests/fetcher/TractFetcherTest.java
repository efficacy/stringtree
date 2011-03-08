package tests.fetcher;

import java.io.File;

import org.stringtree.Tract;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.fetcher.ResourceFetcher;
import org.stringtree.fetcher.TractDirectoryRepository;
import org.stringtree.fetcher.TractResourceFilter;
import org.stringtree.finder.FetcherStringFinder;
import org.stringtree.finder.FetcherTractFinder;
import org.stringtree.finder.TractFinder;
import org.stringtree.tract.EmptyTract;
import org.stringtree.tract.MapTract;

public class TractFetcherTest extends FetcherTestCase {
    
    Tract empty;
    MapFetcher repos;
    TractFinder tf;
    FetcherStringFinder context;

    public TractFetcherTest(String name) {
        super(name);
    }

    public void setUp() {
        empty = EmptyTract.it;
        repos = new MapFetcher();
        context = new FetcherStringFinder(repos);
    }

    public void testTractFinder() {
        MapFetcher sf = new MapFetcher();
        sf.put("present", "hello, world");

        tf = new FetcherTractFinder(sf);

        assertFalse("SFTF contains missing resource", tf.contains("missing"));
        assertEquals("SFTF get on missing resource", empty, tf.get("missing"));

        assertTrue("SFTF contains present resource", tf.contains("present"));
        assertNotNull("SFTF get on present resource not null", tf
                .get("present"));
        assertEquals("SFTF get on present resource", "hello, world", tf.get(
                "present").getContent());
        assertEquals("SFTF get attribute on present resource", null, tf.get(
                "present").getObject("ugh"));
        assertEquals("SFTF get attribute on present resource", "", tf.get(
                "present").get("ugh"));
    }

    public void testResourceTractFetcher() {
        tf = new FetcherTractFinder(new ResourceFetcher(this,
                new TractResourceFilter()));
        assertFalse("RTF contains missing resource", tf.contains("missing"));
        assertEquals("RTF get on missing resource", empty, tf.get("missing"));

        assertTrue("RTF contains present resource", tf.contains("present"));
        assertTrue("RTF get on present resource not null",
                tf.get("present") != null);
        assertEquals("RTF get content on present resource", "hi there", tf.get(
                "present").getContent());
        assertEquals("RTF get attribute on present resource", "whoopee", tf
                .get("present").get("ugh"));
    }

    public void testDirectoryTractFetcher() {
        tf = new FetcherTractFinder(new TractDirectoryRepository(new File(
                "tfdir"), context));

        assertTrue("DTF contains missing resource", tf.contains("missing"));
        assertFalse("DTF contains missing resource", tf.contains("stuff.ugh"));
        assertEquals("DTF get on missing resource", empty, tf.get("missing"));

        assertTrue("DTF contains present resource", tf.contains("present"));
        Tract t = tf.get("present");

        assertTrue("DTF get on present resource not null", t != null);
        assertEquals("DTF get content on present resource", "hello, world", t
                .getContent());
        assertEquals("DTF get missing attribute on present resource", null, t
                .get("xx"));
        assertEquals("DTF get attribute on present resource", "whoopee", t
                .get("ugh"));

        Tract t2 = new MapTract("hello, world");
        t2.put("ugh", "whoopee");
        assertEquals("DTF loaded tract equals local", t2, t);

        assertTrue("DTF contains text resource", tf.contains("stuff"));
        t = tf.get("stuff");
        assertEquals("DTF get content on text resource", "this is text", t
                .getContent());
        t = tf.get("present");
        assertEquals("DTF get content on present resource", "hello, world", t
                .getContent());
    }

    public void testDirectoryTractFetcherAlternativeDirectory() {
        tf = new FetcherTractFinder(new TractDirectoryRepository(new File(
                "tfdir2"), context));
        assertTrue("DTF2 contains missing resource", tf.contains("missing"));
        assertTrue("DTF2 contains present resource", tf.contains("ugh/present"));
    }

    public void testDirectoryTractFetcherIndirect() {
        File dir = new File("tfdir3");
        repos.put(Tract.INDIRECT_ROOT, dir);
        tf = new FetcherTractFinder(new TractDirectoryRepository(dir, context));
        assertTrue("DTFi contains direct resource", tf.contains("direct"));
        assertTrue("DTFi contains indirect resource", tf.contains("indirect"));

        assertEquals("DTFi direct content", "nothing here", tf.get("direct")
                .getContent());
        assertEquals("DTFi direct content", "home on the range", tf.get(
                "indirect").getContent());
        assertEquals("DTFi missing content", null, tf.get("missing")
                .getContent());
    }
}
