package tests.spec;

import java.io.File;

import org.stringtree.Fetcher;
import org.stringtree.finder.SpecFileFetcher;

import junit.framework.TestCase;

public class SpecFetcherTest extends TestCase {

    File dir;
    Fetcher fetcher;

    public void setUp() {
        dir = new File("testfiles/specdir");
    }

    public void testBasic() {
        fetcher = new SpecFileFetcher(new File(dir,"basic.spec"));
        assertEquals(null, fetcher.getObject("unknown"));
        assertEquals("value1", fetcher.getObject("colon"));
        assertEquals("value2", fetcher.getObject("colon space"));
        assertEquals("value3", fetcher.getObject("colon 2 space"));
        assertEquals("value4", fetcher.getObject("equal"));
        assertEquals("value5", fetcher.getObject("equal space"));
        assertEquals("value6", fetcher.getObject("equal 2 space"));
    }
}
