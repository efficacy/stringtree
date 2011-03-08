package tests.jms;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.stringtree.mock.jms.MockMessageListener;

public class TopicSubscriberTest extends MessagingTestCase {
    MockMessageListener a;
    MockMessageListener b;
    MockMessageListener c;
    
    public void setUp() throws JMSException, NamingException {
        a = new MockMessageListener();
        assertEquals(0, a.recorded.size());
        b = new MockMessageListener();
        assertEquals(0, b.recorded.size());
        c = new MockMessageListener();
        assertEquals(0, c.recorded.size());
        jmsc = defineTopics(true);
    }
    
    public void testAddBeforeSendGetsMessage() throws JMSException {
        TextMessage t1 = jmsc.tsession.createTextMessage("one");
        jmsc.tout.subscribe(a);
        jmsc.tpublisher.publish(t1);
        assertEquals(1, a.recorded.size());
    }
    
    public void testAddAfterSendDoesNotGetMessage() throws JMSException {
        TextMessage t1 = jmsc.tsession.createTextMessage("one");
        jmsc.tpublisher.publish(t1);
        jmsc.tout.subscribe(a);
        assertEquals(0, a.recorded.size());
    }
    
    public void testBothSubscribersGetMessage() throws JMSException {
        TextMessage t1 = jmsc.tsession.createTextMessage("one");
        jmsc.tout.subscribe(a);
        jmsc.tout.subscribe(b);
        jmsc.tpublisher.publish(t1);
        assertEquals(1, a.recorded.size());
        assertEquals(1, b.recorded.size());
    }
    
    public void testUnsubscribedDoesNotGetMessage() throws JMSException {
        TextMessage t1 = jmsc.tsession.createTextMessage("one");
        jmsc.tout.subscribe(a);
        jmsc.tout.subscribe(b);
        jmsc.tout.unsubscribe(a);
        jmsc.tpublisher.publish(t1);
        assertEquals(0, a.recorded.size());
        assertEquals(1, b.recorded.size());
    }
    
}
