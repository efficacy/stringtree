package tests.xmlevents;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllXMLTests extends TestCase {
	public static TestSuite suite() {
		TestSuite ret = new TestSuite();

        ret.addTest(new TestSuite(RSSPartialMapXMLTest.class));
        ret.addTest(new TestSuite(PartialMapXMLTest.class));
        ret.addTest(new TestSuite(FullMapXMLTest.class));
        ret.addTest(new TestSuite(TrimmedXMLTest.class));
        ret.addTest(new TestSuite(LaxXMLTest.class));
        ret.addTest(new TestSuite(StrictEventXMLTest.class));

        ret.addTest(new TestSuite(AutoHierarchyHistoryTest.class));
        ret.addTest(new TestSuite(StrictHierarchyHistoryTest.class));
        return ret;
	}
}
