package tests.xmlevents;

import java.io.IOException;
import java.io.StringReader;

import org.stringtree.xmlevents.XMLEvent;
import org.stringtree.xmlevents.XMLEventParser;

public class TrimmedXMLTest extends EventXMLTestCase {

	protected XMLEventParser createParser() {
		return new XMLEventParser(false, true);
	}

	public void testBlankText() throws IOException {
		parser.process(new StringReader("<a>  </a>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN);
		assertNextEvent(XMLEvent.CLOSE);
		assertNextEvent(XMLEvent.END);
		assertNoMoreEvents();
	}

	public void testTextWithSpaces() throws IOException {
		parser.process(new StringReader("<a> hello there  </a>"), handler);
		assertNextEvent(XMLEvent.START);
		assertNextEvent(XMLEvent.OPEN);
		assertNextEvent(XMLEvent.TEXT, XMLEvent.KEY_VALUE, "hello there");
		assertNextEvent(XMLEvent.CLOSE);
		assertNextEvent(XMLEvent.END);
		assertNoMoreEvents();
	}

}
