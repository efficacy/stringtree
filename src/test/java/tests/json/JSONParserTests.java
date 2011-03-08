package tests.json;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JSONParserTests extends TestCase {
    
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        
        suite.addTestSuite(JSONParserObjectTest.class);
        suite.addTestSuite(JSONParserArrayTest.class);
        suite.addTestSuite(JSONParserDirectTest.class);
        suite.addTestSuite(JSONParserStringTest.class);
        suite.addTestSuite(JSONParserNumberTest.class);
        suite.addTestSuite(JSONParserBlankTest.class);

        return suite;
    }

}
