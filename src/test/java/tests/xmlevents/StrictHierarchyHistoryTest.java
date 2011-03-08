package tests.xmlevents;

import org.stringtree.xmlevents.HierarchyHistory;
import org.stringtree.xmlevents.History;

public class StrictHierarchyHistoryTest extends HierarchyHistoryTestCase {

	protected History<String> createHistory() {
		return new HierarchyHistory<String>(false);
	}
	
	public void testStrictBackTwoDifferent() {
		history.forward("hello");
		history.forward("there");
		try {
			history.back("hello");
			fail("should throw IllegalStateException");
		} catch (IllegalStateException e) {
			assertEquals("got back('hello') expected back('there')", e.getMessage());
		}
	}
	
	public void testStrictBackNotFound() {
		history.forward("hello");
		history.forward("hello");
		try {
			history.back("there");
			fail("should throw IllegalStateException");
		} catch (IllegalStateException e) {
			assertEquals("got back('there') expected back('hello')", e.getMessage());
		}
	}

}
