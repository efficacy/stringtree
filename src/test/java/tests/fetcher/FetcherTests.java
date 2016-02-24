package tests.fetcher;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FetcherTests extends TestCase {
    
    public FetcherTests(String name) {
        super(name);
    }

    public static TestSuite suite() {
        TestSuite ret = new TestSuite();

        //ret.addTest(new TestSuite(TractFetcherTest.class));
        ret.addTest(new TestSuite(PeelbackTest.class));
        ret.addTest(new TestSuite(FetcherTest.class));
        ret.addTest(new TestSuite(BeanFetcherTest.class));
        /*
         * ret.addTest(new TestSuite(ListableTest.class)); 
         * ret.addTest(new TestSuite(FilenameFlattenerTest.class)); 
         * ret.addTest(new TestSuite(TractRepositoryTest.class)); 
         * ret.addTest(new TestSuite(TractFetcherTest.class)); 
         * ret.addTest(new TestSuite(StringRepositoryTest.class)); 
         */
        return ret;
    }
}
