package tests.streams;

import java.io.IOException;

import org.stringtree.streams.StreamConverter;
import org.stringtree.streams.StringStreamConverter;

public class SimpleStringStreamTest extends StreamTestCase {
    String string;
    
    protected StreamConverter createConverter() {
        return new StringStreamConverter();
    }

    public void testEmpty() throws IOException {
       string = (String)converter.convert(makeInputStream("empty")).create(context);
       assertEquals("", string);
    }
    
    public void testASCII() throws IOException {
        string = (String)converter.convert(makeInputStream("ascii")).create(context);
        assertEquals("hello", string);
    }
}
