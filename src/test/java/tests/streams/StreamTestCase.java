package tests.streams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.streams.StreamConverter;

public abstract class StreamTestCase extends TestCase {

    protected Fetcher context;
    protected StreamConverter converter;
    protected File base;
    
    protected abstract StreamConverter createConverter();

    protected InputStream makeInputStream(String filename) {
        InputStream ret = null;
        try {
            ret = new FileInputStream(new File(base, filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void testNull() {
        try {
            converter.convert(null);
            fail("should throw  for null input");
        } catch (IOException e) {
            // expected
        }
    }

    public void setUp() {
        converter = createConverter();
        base = new File("testfiles/streams");
        context = new MapFetcher();
    }

}
