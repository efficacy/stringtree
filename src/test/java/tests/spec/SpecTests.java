package tests.spec;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SpecTests extends TestCase {
    
    public static boolean logging = false;

    public SpecTests(String name) {
        super(name);
    }

    public static TestSuite suite() {
        TestSuite ret = new TestSuite();

        ret.addTest(new TestSuite(EnvironmentTest.class));
        ret.addTest(new TestSuite(SpecFetcherTest.class));
        ret.addTest(new TestSuite(SpecReaderTest.class));

        return ret;
    }

    public static void log(String s) {
        if (logging) {
            System.out.println(s);
        }
    }
}
