package org.stringtree.jms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.stringtree.jms.message.InMemoryBytesMessage;
import org.stringtree.jms.message.InMemoryMapMessage;
import org.stringtree.jms.message.InMemoryMessage;
import org.stringtree.jms.message.InMemoryObjectMessage;
import org.stringtree.jms.message.InMemoryStreamMessage;
import org.stringtree.jms.message.InMemoryTextMessage;

public class InMemorySession implements Session {

    private boolean transacted = false;
    private MessageListener listener;
    private int acknowledgeMode;
    protected boolean autostart;
    
    protected List<InMemoryMessageProducer> producers;
    protected List<InMemoryMessageConsumer> consumers;
    
    public InMemorySession(boolean transacted, int acknowledgeMode, boolean autostart) {
        this.transacted = transacted;
        this.acknowledgeMode = acknowledgeMode;
        this.autostart = autostart;
        producers = new ArrayList<InMemoryMessageProducer>();
        consumers = new ArrayList<InMemoryMessageConsumer>();
    }
    
    public InMemorySession(boolean autostart) {
        this(false, Session.AUTO_ACKNOWLEDGE, autostart);
    }

    public QueueBrowser createBrowser(Queue queue) {
         return new InMemoryQueueBrowser(queue);
    }

    public QueueBrowser createBrowser(Queue queue, String name) {
        return new InMemoryQueueBrowser(queue, name);
    }

    public Queue createQueue(String name) {
        return new InMemoryQueue(name);
    }

    public TemporaryQueue createTemporaryQueue() {
         return new InMemoryQueue();
    }

    public Topic createTopic(String name) {
        return new InMemoryTopic(name);
    }

    public TemporaryTopic createTemporaryTopic() {
        return new InMemoryTopic();
    }

    public TopicSubscriber createDurableSubscriber(Topic topic, String selector) {
        InMemoryTopicSubscriber ret = new InMemoryTopicSubscriber(topic, selector);
        ret.setRunning(autostart);
        consumers.add(ret);
        return ret;
    }

    public TopicSubscriber createDurableSubscriber(Topic topic, String selector, String arg2, boolean arg3) {
        InMemoryTopicSubscriber ret = new InMemoryTopicSubscriber(topic, selector);
        ret.setRunning(autostart);
        consumers.add(ret);
        return ret;
    }
    

    public MessageProducer createProducer(Destination destination) throws JMSException {
    	if (destination instanceof Queue) return new InMemoryQueueSender((Queue)destination, autostart);
    	if (destination instanceof Topic) return new InMemoryTopicPublisher((Topic)destination, autostart);
    	return null;
    }

    public MessageConsumer createConsumer(Destination destination) throws JMSException {
    	if (destination instanceof Queue) return new InMemoryQueueReciever((Queue)destination, autostart);
    	if (destination instanceof Topic) return new InMemoryTopicSubscriber((Topic)destination);
    	return null;
    }

    public MessageConsumer createConsumer(Destination destination, String selector) throws JMSException {
    	if (destination instanceof Queue) return new InMemoryQueueReciever((Queue)destination, selector, autostart);
    	if (destination instanceof Topic) return new InMemoryTopicSubscriber((Topic)destination, selector);
    	return null;
    }

    public MessageConsumer createConsumer(Destination destination, String selector, boolean NoLocal) throws JMSException {
    	if (destination instanceof Queue) return new InMemoryQueueReciever((Queue)destination, selector, autostart);
    	if (destination instanceof Topic) return new InMemoryTopicSubscriber((Topic)destination, selector);
    	return null;
    }

    public void unsubscribe(String arg0) {
        // TODO huh?
    }

    public BytesMessage createBytesMessage() {
        return new InMemoryBytesMessage();
    }
    
    public MapMessage createMapMessage() {
        return new InMemoryMapMessage();
    }
    
    public Message createMessage() {
        return new InMemoryMessage();
    }
    
    public ObjectMessage createObjectMessage() {
        return new InMemoryObjectMessage();
    }
    
    public ObjectMessage createObjectMessage(Serializable obj) {
        return new InMemoryObjectMessage(obj);
    }
    
    public StreamMessage createStreamMessage() {
        return new InMemoryStreamMessage();
    }
    
    public TextMessage createTextMessage() {
        return new InMemoryTextMessage();
    }
    
    public TextMessage createTextMessage(String text) {
        return new InMemoryTextMessage(text);
    }
    
    public MessageListener getMessageListener() {
        return listener;
    }

    public void setMessageListener(MessageListener listener) {
        this.listener = listener;
    }
    
    public boolean getTransacted() {
        return transacted;
    }
    
    public int getAcknowledgeMode() {
    	return acknowledgeMode;
    }
    
    public void recover() {
    }
    
    public void rollback() {
    }

    public void close() {
    }
    
    public void commit() {
    }

    public void run() {
    }
    
    public void setRunning(boolean running) {
        for (InMemoryMessageConsumer consumer : consumers) {
            consumer.setRunning(running);
        }
        for (InMemoryMessageProducer producer : producers) {
            producer.setRunning(running);
        }
    }
}
