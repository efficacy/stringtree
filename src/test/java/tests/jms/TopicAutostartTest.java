package tests.jms;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.stringtree.mock.jms.MockMessageListener;

public class TopicAutostartTest extends MessagingTestCase {
    
    MockMessageListener listener;
    
    public void setUp() {
        listener = new MockMessageListener();
        assertEquals(0, listener.recorded.size());
    }
    
	public void testTextMessageHoldThenStart() throws JMSException, NamingException {
        jmsc = defineTopics(false);
        TextMessage tm = jmsc.tsession.createTextMessage("hello");
        assertNotNull(tm);
        assertEquals("hello", tm.getText());

        jmsc.tout.subscribe(listener);
        assertEquals(0, listener.recorded.size());

        jmsc.tpublisher.publish(tm);
        assertEquals(0, listener.recorded.size());
        
        jmsc.tconnection.start();
        assertEquals(1, listener.recorded.size());
        assertEquals(tm, listener.recorded.get(0).arguments[0]);
	}

    public void testTextMessageAutoStart() throws JMSException, NamingException {
        jmsc = defineTopics(true);
        TextMessage tm = jmsc.tsession.createTextMessage("hello");
        assertNotNull(tm);
        assertEquals("hello", tm.getText());

        jmsc.tout.subscribe(listener);
        assertEquals(0, listener.recorded.size());

        jmsc.tpublisher.publish(tm);
        assertEquals(1, listener.recorded.size());
        assertEquals(tm, listener.recorded.get(0).arguments[0]);
    }
}
