package tests.db;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DatabaseTests extends TestCase {
    
    public static TestSuite suite() {
        TestSuite ret = new TestSuite();

        ret.addTestSuite(DatabaseWrapperLoggingTest.class);

        return ret;
    }
}
