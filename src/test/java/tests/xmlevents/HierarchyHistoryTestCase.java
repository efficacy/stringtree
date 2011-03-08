package tests.xmlevents;

import junit.framework.TestCase;

import org.stringtree.util.testing.ChecklistOrdered;
import org.stringtree.xmlevents.History;

public abstract class HierarchyHistoryTestCase extends TestCase {
	History<String> history;
	
	public void setUp() {
		history = createHistory();
	}

	protected abstract History<String> createHistory();
	
	public void testEmpty() {
		assertEquals(0, history.depth());
		assertEquals(null, history.current());
		assertEquals(null, history.previous());
		assertTrue(history.history().isEmpty());
		assertEquals("", history.toPath());
	}
	
	public void testSingle() {
		history.forward("hello");
		assertEquals(1, history.depth());
		assertEquals("hello", history.current());
		assertEquals(null, history.previous());
		assertTrue(new ChecklistOrdered<String>("hello").check(history.history()));
		assertEquals("/hello", history.toPath());
	}
	
	public void testDuplicate() {
		history.forward("hello");
		history.forward("hello");
		assertEquals(2, history.depth());
		assertEquals("hello", history.current());
		assertEquals("hello", history.previous());
		assertTrue(new ChecklistOrdered<String>("hello","hello").check(history.history()));
		assertEquals("/hello/hello", history.toPath());
	}
	
	public void testDifferent() {
		history.forward("hello");
		history.forward("there");
		assertEquals(2, history.depth());
		assertEquals("there", history.current());
		assertEquals("hello", history.previous());
		assertTrue(new ChecklistOrdered<String>("hello","there").check(history.history()));
		assertEquals("/hello/there", history.toPath());
	}
	
	public void testBackOneSame() {
		history.forward("hello");
		history.forward("hello");
		history.back("hello");
		assertEquals(1, history.depth());
		assertEquals("hello", history.current());
		assertEquals(null, history.previous());
		assertTrue(new ChecklistOrdered<String>("hello").check(history.history()));
		assertEquals("/hello", history.toPath());
	}
	
	public void testBackOneDifferent() {
		history.forward("hello");
		history.forward("there");
		history.back("there");
		assertEquals(1, history.depth());
		assertEquals("hello", history.current());
		assertEquals(null, history.previous());
		assertTrue(new ChecklistOrdered<String>("hello").check(history.history()));
		assertEquals("/hello", history.toPath());
	}
	
}
