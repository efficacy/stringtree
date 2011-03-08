package tests.util.sheet;

import junit.framework.TestCase;

import org.stringtree.sheet.SheetModel;

public abstract class SheetModelTest extends TestCase {
	SheetModel model;
	
	public void testEmpty() {
		assertNull(model.get("R1", "C1"));
	}
	
	public void testSingle() {
		model.put("R1", "C1", "hello");
		assertEquals("hello", model.get("R1", "C1"));
	}
	
	public void testDouble() {
		model.put("R1", "C1", "hello");
		model.put("R1", "C2", "there");
		model.put("R2", "C1", "cruel");
		model.put("R2", "C2", "thing, whatever");
		assertEquals("hello", model.get("R1", "C1"));
		assertEquals("there", model.get("R1", "C2"));
		assertEquals("cruel", model.get("R2", "C1"));
		assertEquals("thing, whatever", model.get("R2", "C2"));
	}
}
