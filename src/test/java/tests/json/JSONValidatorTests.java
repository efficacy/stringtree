package tests.json;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JSONValidatorTests extends TestCase {
    
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        
        suite.addTestSuite(JSONValidatorValidTest.class);
        suite.addTestSuite(JSONValidatorInvalidTest.class);
        suite.addTestSuite(JSONValidatorInvalidNumberTest.class);
        suite.addTestSuite(JSONValidatorInvalidStringTest.class);
        suite.addTestSuite(JSONValidatorInvalidArrayTest.class);
        suite.addTestSuite(JSONValidatorInvalidObjectTest.class);

        return suite;
    }

}
