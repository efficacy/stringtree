package tests.tree;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HierarchyTests extends TestCase {

    public static TestSuite suite() {
        TestSuite ret = new TestSuite();

        ret.addTest(new TestSuite(DeepTest.class));
        ret.addTest(new TestSuite(StoreLoadTest.class));
        ret.addTest(new TestSuite(CreateTest.class));

        return ret;
    }
}
