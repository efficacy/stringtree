package tests.streams;

import java.io.IOException;

import org.stringtree.streams.ByteArrayStreamConverter;
import org.stringtree.streams.StreamConverter;

public class ByteArrayStreamTest extends StreamTestCase {
    byte[] bytes;
    
    protected StreamConverter createConverter() {
        return new ByteArrayStreamConverter();
    }

    public void testEmpty() throws IOException {
       bytes = (byte[])converter.convert(makeInputStream("empty")).create(context);
       assertEquals(0, bytes.length);
    }
    
    public void testASCII() throws IOException {
       bytes = (byte[])converter.convert(makeInputStream("ascii")).create(context);
       assertEquals(5, bytes.length);
       assertEquals('h', bytes[0]);
       assertEquals('e', bytes[1]);
       assertEquals('l', bytes[2]);
       assertEquals('l', bytes[3]);
       assertEquals('o', bytes[4]);
    }
}
