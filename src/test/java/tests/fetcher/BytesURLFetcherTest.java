package tests.fetcher;

import java.io.IOException;

import junit.framework.TestCase;

import org.stringtree.fetcher.BytesURLFetcher;
import org.stringtree.util.FileReadingUtils;
import org.stringtree.util.testing.TestHelper;

public class BytesURLFetcherTest extends TestCase {
    
    BytesURLFetcher fetcher;
    
    public void setUp() throws IOException {
        fetcher = new BytesURLFetcher("testfiles/binfiles");
    }

    public void testMissing() {
        assertEquals(null, fetcher.getObject("missing.jpg"));
    }

    public void testPresent() {
        byte[] expected = FileReadingUtils.readBytes("testfiles/binfiles/frank-cropped.jpg");
        TestHelper.assertBytes(expected, (byte[])fetcher.getObject("frank-cropped.jpg"));
    }
}
