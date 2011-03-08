package tests.xmlevents;

import org.stringtree.xmlevents.HierarchyHistory;
import org.stringtree.xmlevents.History;

public class AutoHierarchyHistoryTest extends HierarchyHistoryTestCase {

	protected History<String> createHistory() {
		return new HierarchyHistory<String>(true);
	}
	
	public void testBackTwoDifferent() {
		history.forward("hello");
		history.forward("there");
		assertEquals("there", history.back("hello"));
		assertEquals("hello", history.back("hello"));
		assertEquals(0, history.depth());
		assertEquals(null, history.current());
		assertEquals(null, history.previous());
		assertTrue(history.history().isEmpty());
		assertEquals("", history.toPath());
	}
	
	public void testBackNotFound() {
		history.forward("hello");
		history.forward("hello");
		assertEquals("hello", history.back("there"));
		assertEquals("hello", history.back("there"));
		assertNull(history.back("there"));
		assertEquals(0, history.depth());
		assertEquals(null, history.current());
		assertEquals(null, history.previous());
		assertTrue(history.history().isEmpty());
		assertEquals("", history.toPath());
	}

}
