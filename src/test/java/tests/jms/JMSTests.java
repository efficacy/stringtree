package tests.jms;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JMSTests extends TestCase {
    
    public static TestSuite suite() {
        TestSuite ret = new TestSuite();

        ret.addTestSuite(SimpleMessagerTest.class);
        ret.addTestSuite(TopicSubscriberTest.class);
        ret.addTestSuite(TopicAutostartTest.class);
        ret.addTestSuite(QueueAutostartTest.class);

        return ret;
    }
}
