package tests.fetcher;

import junit.framework.TestCase;

import org.stringtree.fetcher.BytesDirectoryRepository;
import org.stringtree.util.FileReadingUtils;
import org.stringtree.util.testing.TestHelper;

public class BytesDirectoryRepositoryTest extends TestCase {
    
    BytesDirectoryRepository rep;
    
    public void setUp() {
        rep = new BytesDirectoryRepository("testfiles/binfiles");
    }

    public void testMissing() {
        assertEquals(null, rep.getObject("missing.jpg"));
    }

    public void testPresent() {
        byte[] expected = FileReadingUtils.readBytes("testfiles/binfiles/frank-cropped.jpg");
        TestHelper.assertBytes(expected, (byte[])rep.getObject("frank-cropped.jpg"));
    }
}
