package tests.streams;

import java.io.IOException;

import org.stringtree.Tract;
import org.stringtree.streams.StreamConverter;
import org.stringtree.streams.TractStreamConverter;
import org.stringtree.tract.MapTract;

public class TractStreamTest extends StreamTestCase {
    
    protected StreamConverter createConverter() {
        return new TractStreamConverter();
    }

    public void testEmpty() throws IOException {
        assertEquals(new MapTract(""), converter.convert(makeInputStream("empty")).create(context));
    }

    public void testTract() throws IOException {
        Tract tract = new MapTract("hello=world");
        tract.put("a", "b");
        tract.put("c", "d");
        
        assertEquals(tract, converter.convert(makeInputStream("tract")).create(tract));
    }

}
