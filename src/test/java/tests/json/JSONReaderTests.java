package tests.json;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JSONReaderTests extends TestCase {
    
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        
        suite.addTestSuite(JSONReaderOverallTest.class);
        suite.addTestSuite(JSONReaderObjectTest.class);
        suite.addTestSuite(JSONReaderArrayTest.class);
        suite.addTestSuite(JSONReaderDirectTest.class);
        suite.addTestSuite(JSONReaderStringTest.class);
        suite.addTestSuite(JSONReaderNumberTest.class);
        suite.addTestSuite(JSONReaderBlankTest.class);

        return suite;
    }
}
