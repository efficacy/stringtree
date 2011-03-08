package tests.jms;

import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.stringtree.jms.InMemoryConnectionFactory;
import org.stringtree.mock.jms.MockQueue;
import org.stringtree.mock.jms.MockTopic;

class JMSTestContext {
    public Context jndi;
    public InMemoryConnectionFactory factory;

    public MockQueue qin;
    public MockQueue qout;
    public QueueConnection qconnection;
    public QueueSession qsession;
    public QueueReceiver qreceiver;
    public QueueSender qsender;

    public MockTopic tin;
    public MockTopic tout;
    public TopicConnection tconnection;
    public TopicSession tsession;
    public TopicSubscriber tsubscriber;
    public TopicPublisher tpublisher;
}

public abstract class MessagingTestCase extends JNDITestCase {
	
	public Context getJmsJndi() throws NamingException {
		return ensureSubcontext("jms");
	}

    protected JMSTestContext jmsc;
    
    protected JMSTestContext defineInfra(boolean autostart) throws NamingException {
        JMSTestContext ret = new JMSTestContext();

        InMemoryConnectionFactory connectionFactory = new InMemoryConnectionFactory(autostart);
        ret.jndi = getJmsJndi();
        ret.jndi.rebind("QueueConnectionFactory", connectionFactory);

        ret.factory = connectionFactory;
        assertNotNull(ret.factory);

        Context root = new InitialContext();
        assertEquals(ret.factory, root.lookup("jms/QueueConnectionFactory"));
        
        return ret;
    }
    
    protected JMSTestContext defineQueues(boolean autostart) throws NamingException, JMSException {
        JMSTestContext ret = defineInfra(autostart);

        ret.qin = new MockQueue("Q1");
        ret.jndi.rebind("IncomingQ1Queue", ret.qin);
        ret.qout = new MockQueue("Q2");
        ret.jndi.rebind("OutgoingQ2Queue", ret.qout);

        ret.qconnection = ret.factory.createQueueConnection();
        assertNotNull(ret.qconnection);
        ret.qsession = ret.qconnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        assertNotNull(ret.qsession);
        ret.qsender = ret.qsession.createSender(ret.qout);
        assertNotNull(ret.qsender);
                
        Context root = new InitialContext();
        assertEquals(ret.qin, root.lookup("jms/IncomingQ1Queue"));
        assertEquals(ret.qout, root.lookup("jms/OutgoingQ2Queue"));
        
        return ret;
    }
    
    protected JMSTestContext defineTopics(boolean autostart) throws NamingException, JMSException {
        JMSTestContext ret = defineInfra(autostart);

        ret.tin = new MockTopic("T1");
        ret.jndi.rebind("IncomingT1Topic", ret.tin);
        ret.tout = new MockTopic("T2");
        ret.jndi.rebind("OutgoingT2Topic", ret.tout);

        ret.tconnection = ret.factory.createTopicConnection();
        assertNotNull(ret.tconnection);
        ret.tsession = ret.tconnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        assertNotNull(ret.tsession);
        ret.tsubscriber = ret.tsession.createSubscriber(ret.tout);
        assertNotNull(ret.tsubscriber);
        ret.tpublisher = ret.tsession.createPublisher(ret.tout);
        assertNotNull(ret.tpublisher);

        Context root = new InitialContext();
        assertEquals(ret.tin, root.lookup("jms/IncomingT1Topic"));
        assertEquals(ret.tout, root.lookup("jms/OutgoingT2Topic"));
        
        return ret;
    }
}
