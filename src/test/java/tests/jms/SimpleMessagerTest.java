package tests.jms;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.NamingException;

import org.stringtree.jms.InMemoryConnectionFactory;
import org.stringtree.jms.SimpleMessager;
import org.stringtree.mock.jms.MockMessageListener;

public class SimpleMessagerTest extends JNDITestCase {
    SimpleMessager sm;
    
    public void setUp() throws NamingException {
        ensureJNDI("jms/ConnectionFactory", new InMemoryConnectionFactory(true));
        sm = new SimpleMessager("jms/ConnectionFactory");
    }
    
    public void testSendAndBrowseTextMessage() throws NamingException, JMSException {
        Queue q1 = sm.createTemporaryQueue();
        QueueBrowser qb = sm.getQueueBrowser(q1);
        assertFalse(qb.getEnumeration().hasMoreElements());
        
        sm.sendTextMessageToQueue(q1, "hello, q1");
        
        assertTrue(qb.getEnumeration().hasMoreElements());
        TextMessage textMessage = (TextMessage)qb.getEnumeration().nextElement();
        assertNotNull(textMessage.toString());
		assertEquals("hello, q1", textMessage.getText());
    }
    
    public void testSendAndListenForTextMessage() throws NamingException, JMSException {
        Queue q1 = sm.createTemporaryQueue();
        MockMessageListener listener = new MockMessageListener();
        sm.bindListenerToQueue(q1, listener);

        assertTrue(listener.recorded.isEmpty());
        sm.sendTextMessageToQueue(q1, "hello, q1");
        assertFalse(listener.recorded.isEmpty());
    }
    
    public void testSendAndPullTextMessage() throws NamingException, JMSException {
        Queue q1 = sm.createTemporaryQueue();
        assertNull(sm.receiveMessageFromQueue(q1));
        sm.sendTextMessageToQueue(q1, "hello, q1");
        assertNotNull(sm.receiveMessageFromQueue(q1));
    }
    
    public void testPublishAndSubscribe() throws JMSException, NamingException {
        Topic t1 = sm.createTemporaryTopic();
        MockMessageListener listener = new MockMessageListener();
        sm.subscribeListenerToTopic(t1, listener);

        assertTrue(listener.recorded.isEmpty());
        sm.publishTextMessageToTopic(t1, "hello, t1");
        assertFalse(listener.recorded.isEmpty());
    }
}
