package tests.xmlevents;

import java.io.IOException;
import java.io.StringReader;

import org.stringtree.xmlevents.XMLEvent;
import org.stringtree.xmlevents.XMLEventParser;

public class LaxXMLTest extends EventXMLTestCase {

	protected XMLEventParser createParser() {
		return new XMLEventParser(true, false);
	}
	
	public void testSingle() throws IOException {
		parser.process(new StringReader("<a>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
		assertNoMoreEvents();
	}
	
	public void testDouble() throws IOException {
		parser.process(new StringReader("<a><b>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertNextEvent(XMLEvent.OPEN, "/a/b");
		assertNextEvent(XMLEvent.CLOSE, "/a/b");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
		assertNoMoreEvents();
	}

	public void testUnterminated() throws IOException {
		parser.process(new StringReader("<a><b></a>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertNextEvent(XMLEvent.OPEN, "/a/b");
		assertNextEvent(XMLEvent.CLOSE, "/a/b");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
		assertNoMoreEvents();
	}

	public void testNestedUnterminated() throws IOException {
		parser.process(new StringReader("<a>ugh<b>thing<b>hello</a>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "ugh");
		assertNextEvent(XMLEvent.OPEN, "/a/b");
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "thing");
		assertNextEvent(XMLEvent.OPEN, "/a/b/b");
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "hello");
		assertNextEvent(XMLEvent.CLOSE, "/a/b/b");
		assertNextEvent(XMLEvent.CLOSE, "/a/b");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
		assertNoMoreEvents();
	}

	public void testUnfinished() throws IOException {
		parser.process(new StringReader("<a>hello"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN, "/a");
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "hello");
		assertNextEvent(XMLEvent.CLOSE, "/a");
		assertNextEvent(XMLEvent.END);
		assertNoMoreEvents();
	}

}
