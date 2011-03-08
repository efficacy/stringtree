package tests.json;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JSONWriterTests extends TestCase {
    
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        
        suite.addTestSuite(JSONWriterMapTest.class);
        suite.addTestSuite(JSONWriterArrayTest.class);
        suite.addTestSuite(JSONWriterDirectTest.class);
        suite.addTestSuite(JSONWriterStringTest.class);
        suite.addTestSuite(JSONWriterNumberTest.class);

        return suite;
    }

}
