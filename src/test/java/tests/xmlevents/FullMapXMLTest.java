package tests.xmlevents;

import java.io.IOException;
import java.io.StringReader;

import junit.framework.TestCase;
import org.stringtree.xmlevents.FullMapXMLEventHandler;
import org.stringtree.xmlevents.XMLEventParser;

public class FullMapXMLTest extends TestCase {
	XMLEventParser parser;
	FullMapXMLEventHandler map;
	
	public void setUp() {
		parser = new XMLEventParser(true, true);
		map = new FullMapXMLEventHandler();
	}
	
	public void testEmpty() throws IOException {
		parser.process(new StringReader(""), map);
		assertTrue(map.isEmpty());
	}
	
	public void testSingle() throws IOException {
		parser.process(new StringReader("<a>hello</a>"), map);
		assertEquals("hello", map.get("/a"));
	}
    
    public void testSingleWithNamespace() throws IOException {
        String xml = "<thing:a zot:ugh='xx'>hello</thing:a>";
        
        parser.setParameter(XMLEventParser.KEY_STRIPNS);
        parser.process(new StringReader(xml), map);
        assertEquals("hello", map.get("/a"));
        assertEquals(null, map.get("/thing:a"));
        assertEquals("xx", map.get("/a/@ugh"));
        assertEquals(null, map.get("/a/@zot:ugh"));

        map.clear();
        parser.resetParameter(XMLEventParser.KEY_STRIPNS);
        parser.process(new StringReader(xml), map);
        assertEquals(null, map.get("/a"));
        assertEquals("hello", map.get("/thing:a"));
        assertEquals(null, map.get("/thing:a/@ugh"));
        assertEquals("xx", map.get("/thing:a/@zot:ugh"));
    }
	
	public void testSingleWithCdata() throws IOException {
		parser.process(new StringReader("<a><![CDATA[ <em>markup</em>]]></a>"), map);
		assertEquals(" <em>markup</em>", map.get("/a"));
	}
	
	public void testSingleWithAttributes() throws IOException {
		parser.process(new StringReader("<a x='y' z='qq'>hello</a>"), map);
		assertEquals("hello", map.get("/a"));
		assertEquals("y", map.get("/a/@x"));
		assertEquals("qq", map.get("/a/@z"));
	}

	
	public void testArray() throws IOException {
		parser.process(new StringReader("<a><b>hello</b><b>world</b></a>"), map);
		assertEquals("2", map.get("/a/b.count"));
		assertEquals("hello", map.get("/a/b"));
		assertEquals("hello", map.get("/a/b[0]"));
		assertEquals("world", map.get("/a/b[1]"));
	}
	
	public void testSplitText() throws IOException {
		parser.process(new StringReader("<a>before<b>hello</b>after</a>"), map);
		assertEquals("1", map.get("/a.count"));
		assertEquals("1", map.get("/a/b.count"));
		assertEquals("hello", map.get("/a/b"));
		assertEquals("beforeafter", map.get("/a"));
		assertEquals("beforeafter", map.get("/a[0]"));
	}

	public void testSingleWithUppercaseDeclaration() throws IOException {
        parser.process(new StringReader("<?XML version=\"1.0\"?><a>hello</a>"), map);
        assertEquals("hello", map.get("/a"));
    }

    public void testSingleWithLowercaseDeclaration() throws IOException {
        parser.process(new StringReader("<?xml version=\"1.0\"?><a>hello</a>"), map);
        assertEquals("hello", map.get("/a"));
    }}
