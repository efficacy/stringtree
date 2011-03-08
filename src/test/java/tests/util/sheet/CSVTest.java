package tests.util.sheet;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.stringtree.sheet.SingleMapSheetModel;
import org.stringtree.sheet.QCSVSheet;

import junit.framework.TestCase;

public class CSVTest extends TestCase {
	QCSVSheet sheet;
	
	public void setUp() {
		sheet = new QCSVSheet(new SingleMapSheetModel());
	}
	
	public void testLoadEmpty() {
		sheet.load("");
		assertNull(sheet.get("R1", "C1"));
	}
	
	public void testLoadSingle() {
		sheet.load("-,C1\nR1,hello");
		assertEquals("hello", sheet.get("R1", "C1"));
	}
	
	public void testLoadDouble() {
		sheet.load("-,C1,C2\nR1,hello,there\nR2, cruel ,\"thing, whatever\"");
		assertEquals("hello", sheet.get("R1", "C1"));
		assertEquals("there", sheet.get("R1", "C2"));
		assertEquals("cruel", sheet.get("R2", "C1"));
		assertEquals("thing, whatever", sheet.get("R2", "C2"));
	}
	
	public void testLoadFile() throws FileNotFoundException {
		sheet.load(new FileReader("testfiles/csv1.csv"));
		assertEquals("hello", sheet.get("R1", "C1"));
		assertEquals("there", sheet.get("R1", "C2"));
		assertEquals("cruel", sheet.get("R2", "C1"));
		assertEquals("thing, whatever", sheet.get("R2", "C2"));
	}
}
