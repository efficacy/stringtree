package tests.xmlevents;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.stringtree.util.testing.ChecklistOrdered;
import org.stringtree.xmlevents.PartialMapXMLEventHandler;
import org.stringtree.xmlevents.StanzaMatcher;
import org.stringtree.xmlevents.XMLEventParser;

public class PartialMapXMLTest extends TestCase {
	XMLEventParser parser;
	PartialMapXMLEventHandler handler;
	Map<String, StanzaMatcher> matchers;
	List<String> messages;
	
	public void setUp() {
		parser = new XMLEventParser(true, true);
		messages = new ArrayList<String>();
		matchers = new HashMap<String, StanzaMatcher>();
		matchers.put("/a", new StanzaMatcher() {
			public Object match(String path, Map<?,?> values, Object context) {
				messages.add("match /a, text=" + values.get("/a"));
				return context;
			}});
		matchers.put("/b", new StanzaMatcher() {
			public Object match(String path, Map<?,?> values, Object context) {
				messages.add("match /b, text=" + values.get("/a"));
				messages.add("match /b, x=" + values.get("/a/@x"));
				return context;
			}});
		handler = new PartialMapXMLEventHandler(matchers, false);
	}
	
	public void testEmpty() throws IOException {
		parser.process(new StringReader(""), handler);
		assertTrue(messages.isEmpty());
	}
	
	public void testSingle() throws IOException {
		parser.process(new StringReader("<a>hello</a>"), handler);
		assertTrue(new ChecklistOrdered<String>("match /a, text=hello").check(messages));
	}
}
