package org.stringtree.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SimpleMessager {
    private ConnectionFactory factory;

    private QueueSession queueSession;
    private QueueSender queueSender;
    private QueueConnection queueConnection;

    private TopicSession topicSession;
    private TopicPublisher topicPublisher;
    private TopicConnection topicConnection;

    private Map<String, Queue> queueCache;
    private Map<Object, QueueReceiver> receiverCache;
    private Map<String, Topic> topicCache;
    private Map<Object, TopicSubscriber> subscriberCache;
    
    public SimpleMessager(String factoryJndiName) throws NamingException {
        this((ConnectionFactory) new InitialContext().lookup(factoryJndiName));
    }

    public SimpleMessager(ConnectionFactory factory) {
        this.factory = factory;
        queueCache = new HashMap<String, Queue>();
        receiverCache = new HashMap<Object, QueueReceiver>();
        topicCache = new HashMap<String, Topic>();
        subscriberCache = new HashMap<Object, TopicSubscriber>();
    }
    
    public void sendTextMessageToQueue(Queue queue, String text) throws JMSException {
        Message message = ensureQueueSession().createTextMessage(text);
        ensureQueueSender().send(queue, message);
    }
    
    public void sendTextMessageToQueue(String queueJndiName, String text) throws NamingException, JMSException {
    	sendTextMessageToQueue(getQueue(queueJndiName), text);
    }
    
    public QueueBrowser getQueueBrowser(Queue queue) throws JMSException {
        return ensureQueueSession().createBrowser(queue);
    }
    
    public QueueBrowser getQueueBrowser(String queueJndiName) throws JMSException, NamingException {
        return getQueueBrowser(getQueue(queueJndiName));
    }

    public Queue createTemporaryQueue() throws JMSException, NamingException {
        return ensureQueueSession().createTemporaryQueue();
    }

    public Queue getQueue(String queueJndiName) throws NamingException {
        if (queueCache.containsKey(queueJndiName)) return queueCache.get(queueJndiName);
        
        Queue ret = (Queue) new InitialContext().lookup(queueJndiName);
        queueCache.put(queueJndiName, ret);
        return ret;
    }

    public void bindListenerToQueue(Queue queue, MessageListener listener) throws JMSException, NamingException {
        ensureReceiver(queue).setMessageListener(listener);
    }

    public void bindListenerToQueue(String queueJndiName, MessageListener listener) throws JMSException, NamingException {
        ensureReceiver(queueJndiName).setMessageListener(listener);
    }

    public Message receiveMessageFromQueue(Queue queue) throws JMSException, NamingException {
        return ensureReceiver(queue).receive();
    }

    public Message receiveMessageFromQueue(String queueJndiName) throws JMSException, NamingException {
        return ensureReceiver(queueJndiName).receive();
    }

    public Topic createTemporaryTopic() throws JMSException, NamingException {
        return ensureTopicSession().createTemporaryTopic();
    }

    public Topic getTopic(String topicJndiName) throws NamingException {
        if (topicCache.containsKey(topicJndiName)) return topicCache.get(topicJndiName);
        
        Topic ret = (Topic) new InitialContext().lookup(topicJndiName);
        topicCache.put(topicJndiName, ret);
        return ret;
    }

    public void subscribeListenerToTopic(Topic topic, MessageListener listener) throws JMSException {
        ensureSubscriber(topic).setMessageListener(listener);
    }

    public void subscribeListenerToTopic(String topicJndiName, MessageListener listener) throws NamingException, JMSException {
        ensureSubscriber(topicJndiName).setMessageListener(listener);
    }

    public void publishTextMessageToTopic(Topic topic, String text) throws NamingException, JMSException {
        Message message = ensureTopicSession().createTextMessage(text);
        ensureTopicPublisher().publish(topic, message);
    }

    public void publishTextMessageToTopic(String topicJndiName, String text) throws NamingException, JMSException {
    	publishTextMessageToTopic(getTopic(topicJndiName), text);
    }

    private QueueReceiver ensureReceiver(Queue queue) throws JMSException, NamingException {
        if (receiverCache.containsKey(queue)) return receiverCache.get(queue);
        
        QueueReceiver ret = ensureQueueSession().createReceiver(queue);
        receiverCache.put(queue, ret);
        return ret;
    }

    private QueueReceiver ensureReceiver(String queueJndiName) throws JMSException, NamingException {
        if (receiverCache.containsKey(queueJndiName)) return receiverCache.get(queueJndiName);
        
        return ensureReceiver(getQueue(queueJndiName));
    }

    private QueueSender ensureQueueSender() throws JMSException {
        if (null != queueSender) {
            return queueSender;
        }
        
        QueueSession session = ensureQueueSession();
        queueSender = session.createSender(null);
        return queueSender;
    }

    private QueueSession ensureQueueSession() throws JMSException {
        if (null != queueSession) {
            return queueSession;
        }
        
        QueueConnection queueConnection = ensureQueueConnection();
        queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        return queueSession;
    }

    private QueueConnection ensureQueueConnection() throws JMSException {
        if (null != queueConnection) {
            return queueConnection;
        }
        
        queueConnection = ((QueueConnectionFactory)factory).createQueueConnection();
        queueConnection.start();
        return queueConnection;
    }

    private TopicPublisher ensureTopicPublisher() throws JMSException {
        if (null != topicPublisher) {
            return topicPublisher;
        }
        
        TopicSession session = ensureTopicSession();
        topicPublisher = session.createPublisher(null);
        return topicPublisher;
    }

    private TopicSubscriber ensureSubscriber(Topic topic) throws JMSException {
        if (subscriberCache.containsKey(topic)) return subscriberCache.get(topic);
        
        TopicSubscriber ret = ensureTopicSession().createSubscriber(topic);
        subscriberCache.put(topic, ret);
        return ret;
    }

    private TopicSubscriber ensureSubscriber(String topicJndiName) throws JMSException, NamingException {
        if (subscriberCache.containsKey(topicJndiName)) return subscriberCache.get(topicJndiName);
        return ensureSubscriber(getTopic(topicJndiName));
    }

    private TopicSession ensureTopicSession() throws JMSException {
        if (null != topicSession) {
            return topicSession;
        }
        
        TopicConnection topicConnection = ensureTopicConnection();
        topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        return topicSession;
    }

    private TopicConnection ensureTopicConnection() throws JMSException {
        if (null != topicConnection) {
            return topicConnection;
        }
        
        topicConnection = ((TopicConnectionFactory)factory).createTopicConnection();
        topicConnection.start();
        return topicConnection;
    }
}
