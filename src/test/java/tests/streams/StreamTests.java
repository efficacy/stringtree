package tests.streams;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class StreamTests extends TestCase {
    
    public static TestSuite suite() {
        TestSuite ret = new TestSuite();

        ret.addTestSuite(TractStreamTest.class);
        ret.addTestSuite(CharsetStringStreamTest.class);
        ret.addTestSuite(SimpleStringStreamTest.class);
        ret.addTestSuite(ByteArrayStreamTest.class);

        return ret;
    }
}
