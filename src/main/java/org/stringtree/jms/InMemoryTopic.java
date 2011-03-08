package org.stringtree.jms;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;

public class InMemoryTopic extends InMemoryDestination implements Topic, TemporaryTopic {

    protected List<MessageListener> listeners;

    public InMemoryTopic(String name) {
        super(name);
        listeners = new ArrayList<MessageListener>();
    }

    public InMemoryTopic() {
        this("unnamed topic");
    }
    
    public String getTopicName() {
        return getName();
    }

    public void sendMessage(Message message) {
        if (null != listeners && !listeners.isEmpty()) {
            for (MessageListener listener : listeners) {
                listener.onMessage(message);
            }
        }
    }

    public void subscribe(MessageListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(MessageListener listener) {
        listeners.remove(listener);
    }

    public void reset() {
        listeners.clear();
    }

    public int size() {
        return listeners.size();
    }
}
