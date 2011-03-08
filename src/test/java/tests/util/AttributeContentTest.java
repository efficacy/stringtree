package tests.util;

import java.io.ByteArrayOutputStream;
import java.io.FilterReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;

import junit.framework.TestCase;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.tract.MapTract;
import org.stringtree.tract.ReaderTractReader;
import org.stringtree.tract.TractWriter;

class NoisyReader extends FilterReader {
    
    public NoisyReader(Reader in) {
        super(in);
    }

    public int read() throws IOException {
        int c = super.read();
        System.out.println("noisy reader read char '" + c + "'");
        return c;
    }

    public int read(char cbuf[], int off, int len) throws IOException {
        int ret = super.read(cbuf, off, len);
        for (int i = 0; i < ret; ++i) {
            System.out.println("noisy reader read buf char '"
                    + (int) cbuf[off + i] + "'");
        }
        return ret;
    }

    public void close() throws IOException {
        System.out.println("noisy reader close");
        super.close();
    }
}

public class AttributeContentTest extends TestCase {
    
    private Tract ac;
    private String tc0 = "";
    private String tc1 = "\nhello";
    private String tc2 = "\nhello\ngoodbye";
    private String[] th0 = { "size:10", " size:10", "size :10", "size: 10",
            " size :10", "size : 10", "#huh\nsize:10" };
    private String tb0 = "size:10\ntext:some\\r\\nmore\\nl\\\\nes \ncolour:red\n\nsome\ncontent";

    public AttributeContentTest(String name) {
        super(name);
    }

    public void setUp() {
        ac = new MapTract();
    }

    private void load(Tract ac, String string) throws IOException {
        ac.clear();
        ReaderTractReader.load(ac, new StringReader(string), null);
    }

    private void store(Tract ac, OutputStream out) throws IOException {
        TractWriter.store(ac, out, (Fetcher) null);
    }

    public void testContent() {
        assertEquals("AttributeContent.empty content 1", "", ac.getContent());
        ac.setContent("hello");
        assertEquals("AttributeContent.full content 1", "hello", ac
                .getContent());
        ac.clear();
        assertEquals("AttributeContent.empty content 2", "", ac.getContent());
    }

    public void testAttributes() {
        assertEquals("AttributeContent.empty attr 1", null, ac
                .getObject("size"));
        ac.put("size", "14");
        assertEquals("AttributeContent.full attr 1", "14", ac.getObject("size"));
        ac.clear();
        assertEquals("AttributeContent.empty attr 2", null, ac
                .getObject("size"));
    }

    public void testReadContent() throws IOException {
        load(ac, tc1);
        assertEquals("AttributeContent.content load 3", null, ac
                .getObject("size"));
        assertEquals("AttributeContent.content load 4", "hello", ac
                .getContent());

        load(ac, tc0);
        assertEquals("AttributeContent.content load 1", null, ac
                .getObject("size"));
        assertEquals("AttributeContent.content load 2", "", ac.getContent());

        load(ac, tc2);
        assertEquals("AttributeContent.content load 5", null, ac
                .getObject("size"));
        assertEquals("AttributeContent.content load 6", "hello\ngoodbye", ac
                .getContent());

        load(ac, tc0);
        assertEquals("AttributeContent.content no bleed-through 1", null, ac
                .getObject("size"));
        assertEquals("AttributeContent.content no bleed-through 2", "", ac
                .getContent());
    }

    public void testReadHeaders() throws IOException {
        for (int i = 0; i < th0.length; ++i) {
            load(ac, th0[i]);
            assertEquals("AttributeContent.header load " + i + "a", null, ac
                    .getObject("colour"));
            assertEquals("AttributeContent.header load " + i + "b", "10", ac
                    .getObject("size"));
        }
    }

    public void testReadBoth() throws IOException {
        load(ac, tb0);
        assertEquals("AttributeContent.both load 1", "10", ac.getObject("size"));
        assertEquals("AttributeContent.both load 2", "some\r\nmore\nl\\nes", ac
                .getObject("text"));
        assertEquals("AttributeContent.both load 3", "red", ac
                .getObject("colour"));
        assertEquals("AttributeContent.both load 4", "some\ncontent", ac
                .getContent());

        load(ac, tc0);
        assertEquals("AttributeContent.both no bleed-through 1", null, ac
                .getObject("size"));
        assertEquals("AttributeContent.both no bleed-through 2", "", ac
                .getContent());
    }

    public void testWrite() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        load(ac, tb0);
        store(ac, out);
        Tract ac1 = new MapTract();
        load(ac1, out.toString());

        assertEquals("AttributeContent.store/load 1", "10", ac1
                .getObject("size"));
        assertEquals("AttributeContent.store/load 2", "some\r\nmore\nl\\nes",
                ac1.getObject("text"));
        assertEquals("AttributeContent.store/load 3", "red", ac1
                .getObject("colour"));
        assertEquals("AttributeContent.store/load 4", "some\ncontent", ac1
                .getContent());
    }
}
