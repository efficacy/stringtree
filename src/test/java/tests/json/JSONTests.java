package tests.json;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JSONTests extends TestCase {
    
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        
        suite.addTest(JSONValidatorTests.suite());
        suite.addTest(JSONWriterTests.suite());
        suite.addTest(JSONReaderTests.suite());
        suite.addTest(JSONParserTests.suite());
        suite.addTestSuite(JSONPartialParserTests.class);
        
        return suite;
    }
}
