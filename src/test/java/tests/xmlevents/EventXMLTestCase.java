package tests.xmlevents;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

import junit.framework.TestCase;

import org.stringtree.xmlevents.RecordingXMLEventHandler;
import org.stringtree.xmlevents.XMLEvent;
import org.stringtree.xmlevents.XMLEventParser;
import org.stringtree.xmlevents.XMLEventRecord;

public abstract class EventXMLTestCase extends TestCase {
	
	XMLEventParser parser;
	RecordingXMLEventHandler handler;
	int n;
	
	public void setUp() {
		parser = createParser();
		handler = new RecordingXMLEventHandler();
		n = -1;
	}

	protected abstract XMLEventParser createParser();

	protected void assertEvent(XMLEvent type, int i) {
		XMLEventRecord record = handler.events.get(i);
		assertEquals(type, record.getEvent());
	}

	protected void assertNextEvent(XMLEvent type) {
		assertEvent(type, ++n);
	}

	protected void assertEvent(XMLEvent type, int i, String value) {
		XMLEventRecord record = handler.events.get(i);
		assertEquals(type, record.getEvent());
		assertEquals(value, record.getHistory().toPath());
	}

	protected void assertNextEvent(XMLEvent type, String path) {
		assertEvent(type, ++n, path);
	}

	protected void assertSameEvent(XMLEvent type, String path) {
		assertEvent(type, n, path);
	}

	protected void assertEvent(XMLEvent type, int i, String key, String value) {
		XMLEventRecord record = handler.events.get(i);
		assertEquals(type, record.getEvent());
		assertEquals(value, record.getArgs().get(key));
	}

	protected void assertNextEvent(XMLEvent type, String key, String value) {
		assertEvent(type, ++n, key, value);
	}

	protected void assertSameEvent(XMLEvent type, String key, String value) {
		assertEvent(type, n, key, value);
	}
	
	protected void assertNoMoreEvents() {
		assertTrue(handler.events.size() == n+1);
	}
	
	public void testEmpty() throws IOException {
		parser.process(new StringReader(""), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.END);
		assertNoMoreEvents();
	}
	
	public void testOpenClose() throws IOException {
		parser.process(new StringReader("<a></a>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
		assertNoMoreEvents();
	}
	
	public void testSingleton() throws IOException {
		parser.process(new StringReader("<a/>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertSameEvent(XMLEvent.OPEN, XMLEvent.KEY_SINGLETON, "true");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
		assertNoMoreEvents();
	}
	
	public void testSingletonWithAttributes() throws IOException {
		parser.process(new StringReader("<a x='y'/>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertSameEvent(XMLEvent.OPEN, XMLEvent.KEY_SINGLETON, "true");
		assertSameEvent(XMLEvent.OPEN, "x", "y");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
		assertNoMoreEvents();
	}
	
	public void testOpenCloseWithText() throws IOException {
		parser.process(new StringReader("<a>hello</a>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertNextEvent(XMLEvent.TEXT, "/a");
		assertSameEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "hello");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
		assertNoMoreEvents();
	}
	
	public void testOpenCloseWithTextAndEntity() throws IOException {
		parser.process(new StringReader("<a>hel&amp;lo&lt;</a>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertNextEvent(XMLEvent.TEXT, "/a");
		assertSameEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "hel&lo<");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
		assertNoMoreEvents();
	}
    
    public void testEntityEvents() throws IOException {
        parser.setParameter(XMLEventParser.KEY_ENTITIES);
        parser.process(new StringReader("<a>hel&#123;lo&lt;</a>"), handler);
        assertNextEvent(XMLEvent.START);
        assertNextEvent(XMLEvent.OPEN, "/a");
        assertNextEvent(XMLEvent.TEXT, "/a");
        assertSameEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "hel");
        assertNextEvent(XMLEvent.ENTITY, "/a");
        assertSameEvent(XMLEvent.ENTITY, XMLEvent.KEY_VALUE, "#123");
        assertNextEvent(XMLEvent.TEXT, "/a");
        assertSameEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "lo");
        assertNextEvent(XMLEvent.ENTITY, "/a");
        assertSameEvent(XMLEvent.ENTITY, XMLEvent.KEY_VALUE, "lt");
        assertNextEvent(XMLEvent.CLOSE, "/a");
        assertNextEvent(XMLEvent.END);
        assertNoMoreEvents();
    }
	
	public void testNested() throws IOException {
		parser.process(new StringReader("<a>ugh<b>thing</b>hello</a>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "ugh");
		assertNextEvent(XMLEvent.OPEN, "/a/b");
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "thing");
		assertNextEvent(XMLEvent.CLOSE, "/a/b");
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "hello");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
		assertNoMoreEvents();
	}
	
	public void testDoctype() throws IOException {
		parser.process(new StringReader("<!DOCTYPE web-app PUBLIC\n  \"-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN\"\n		 \"http://java.sun.com/dtd/web-app_2_3.dtd\" ><a>ugh</a>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.DOCTYPE, XMLEvent.KEY_VALUE, "DOCTYPE web-app PUBLIC\n  \"-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN\"\n		 \"http://java.sun.com/dtd/web-app_2_3.dtd\" ");
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "ugh");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
	}
	
	public void testComment() throws IOException {
		parser.process(new StringReader("<a>u<!-- whatever -->gh</a>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "u");
		assertNextEvent(XMLEvent.COMMENT, XMLEvent.KEY_VALUE, "whatever");
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "gh");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
	}
	
	public void testSimpleCdata() throws IOException {
		parser.process(new StringReader("<a><![CDATA[ <em>markup</em>]]></a>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, " <em>markup</em>");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
	}
	
	public void testMixedCdata() throws IOException {
		parser.process(new StringReader("<a>ugh<![CDATA[ <em>markup</em>]]>zot</a>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "ugh");
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, " <em>markup</em>");
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "zot");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
	}
    
    public void testWithUppercaseDeclaration() throws IOException {
        parser.process(new StringReader("<?XML version=\"1.0\"?><a>hello</a>"), handler);
        assertNextEvent(XMLEvent.START);
        assertNextEvent(XMLEvent.XML);
        assertNextEvent(XMLEvent.OPEN, "/a");
        assertNextEvent(XMLEvent.TEXT, "/a");
        assertSameEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "hello");
        assertNextEvent(XMLEvent.CLOSE, "/a");
        assertNextEvent(XMLEvent.END);
        assertNoMoreEvents();
    }
    
    public void testWithLowercaseDeclaration() throws IOException {
        parser.process(new StringReader("<?xml version=\"1.0\"?><a>hello</a>"), handler);
        assertNextEvent(XMLEvent.START);
        assertNextEvent(XMLEvent.XML);
        assertSameEvent(XMLEvent.XML, XMLEvent.KEY_SINGLETON, "true");
        assertNextEvent(XMLEvent.OPEN, "/a");
        assertSameEvent(XMLEvent.OPEN, XMLEvent.KEY_SINGLETON, null);
        assertNextEvent(XMLEvent.TEXT, "/a");
        assertSameEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "hello");
        assertNextEvent(XMLEvent.CLOSE, "/a");
        assertNextEvent(XMLEvent.END);
        assertNoMoreEvents();
    }
    
    public void testOddGDPXML2() throws FileNotFoundException, IOException {
        parser.process(new StringReader("<x><p>Year's</p><a b='z/z' c=\"u/g/h\"/></x>"), handler);
    }
}

