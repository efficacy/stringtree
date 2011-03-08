package org.stringtree.jms;

import javax.jms.Message;
import javax.jms.Topic;
import javax.jms.TopicPublisher;

public class InMemoryTopicPublisher extends InMemoryMessageProducer implements TopicPublisher {
    
    public InMemoryTopicPublisher(Topic topic, boolean autostart) {
        super(topic, autostart);
    }

    public Topic getTopic() {
        return (Topic)getDestination();
    }

    public void publish(Message message) {
        sendOrDefer(getDestination(), message);
    }

    public void publish(Topic topic, Message message) {
        sendOrDefer(topic, message);
    }

    public void publish(Message message, int arg1, int arg2, long arg3) {
        sendOrDefer(getDestination(), message);
    }

    public void publish(Topic topic, Message message, int arg2, int arg3, long arg4) {
        sendOrDefer(topic, message);
    }
}
