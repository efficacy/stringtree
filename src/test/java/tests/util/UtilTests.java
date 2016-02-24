package tests.util;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UtilTests extends TestCase {
    
    public static TestSuite suite() {
        TestSuite ret = new TestSuite();

        ret.addTest(new TestSuite(LimitedHashMapTest.class));
        ret.addTest(new TestSuite(PrefixSequentialIdSourceTest.class));
        ret.addTest(new TestSuite(EndOfTheLineTest.class));
        ret.addTest(new TestSuite(StopWatchTest.class));
        ret.addTest(new TestSuite(GuidTest.class));
        ret.addTest(new TestSuite(StringSplitterTest.class));
        ret.addTest(new TestSuite(ClassUtilTest.class));
        ret.addTest(new TestSuite(MethodCallTest.class));
        ret.addTest(new TestSuite(TractTest.class));
        ret.addTest(new TestSuite(AttributeContentTest.class));
        ret.addTest(new TestSuite(CacheTest.class));
        ret.addTest(new TestSuite(TreeTest.class));
        ret.addTest(new TestSuite(TreeWalkerTest.class));
        ret.addTest(new TestSuite(UtilTest.class));
        ret.addTest(new TestSuite(ArrayIteratorTest.class));
        ret.addTest(new TestSuite(ChecklistTest.class));
        ret.addTest(new TestSuite(LoggerTest.class));
        ret.addTest(new TestSuite(SortedIteratorIteratorTest.class));
        ret.addTest(new TestSuite(SorterTest.class));

        return ret;
    }
}
