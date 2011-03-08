package tests.fetcher;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.ContainerHelper;

import junit.framework.TestCase;

public class FetcherTestCase extends TestCase {
    
    public FetcherTestCase() {
        super();
    }

    public FetcherTestCase(String arg0) {
        super(arg0);
    }

    public void assertContains(String text, boolean expected, Fetcher fetcher,
            String name) {
        assertEquals(text, expected, ContainerHelper.contains(fetcher, name));
    }
}
