package tests.util.sheet;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SheetTests extends TestCase {
	public static TestSuite suite() {
		TestSuite ret = new TestSuite();

        ret.addTest(new TestSuite(SingleMapSheetModelTest.class));
        ret.addTest(new TestSuite(MultipleMapSheetModelTest.class));
        ret.addTest(new TestSuite(ListSheetModelTest.class));
        ret.addTest(new TestSuite(CSVTest.class));
        return ret;
	}
}
