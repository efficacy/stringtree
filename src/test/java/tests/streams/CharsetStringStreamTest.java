package tests.streams;

import java.io.IOException;

import org.stringtree.streams.StreamConverter;
import org.stringtree.streams.StringStreamConverter;

public class CharsetStringStreamTest extends StreamTestCase {
    
    protected StreamConverter createConverter() {
        return new StringStreamConverter("UTF-8");
    }

    public void testEmpty() throws IOException {
       assertEquals("", (String)converter.convert(makeInputStream("empty")).create(context));
    }
    
    public void testASCII() throws IOException {
        assertEquals("hello", converter.convert(makeInputStream("ascii")).create(context));
    }
    
    public void testUTF8() throws IOException {
        assertEquals("\u00a325\u00a3", converter.convert(makeInputStream("utf8")).create(context));
    }
}


