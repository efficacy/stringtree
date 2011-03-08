package tests.xmlevents;

import org.stringtree.xmlevents.XMLEventParser;

public class StrictEventXMLTest extends EventXMLTestCase {

	protected XMLEventParser createParser() {
		return new XMLEventParser(false, false);
	}
}
