package org.stringtree.jms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;

import org.stringtree.jms.message.InMemoryMessage;

public class InMemoryQueue extends InMemoryDestination implements Queue, TemporaryQueue, Iterable<Message> {

    protected List<Message> messages;
    protected MessageListener listener;

    public InMemoryQueue(String name) {
        super(name);
        reset();
    }

    public InMemoryQueue() {
        this("unnamed queue");
    }
    
    public String getQueueName() {
        return getName();
    }

    public void sendMessage(Message message) {
        message = (Message)((InMemoryMessage)message).clone();
        if (null == listener) {
            messages.add((Message)((InMemoryMessage)message).clone());
        } else {
            listener.onMessage(message);
        }
    }

    public int size() {
        return messages.size();
    }

    public Message peek() {
        return messages.get(0);
    }

    public Message recieve() {
        return messages.remove(0);
    }

    public void reset() {
        messages = new ArrayList<Message>();
    }
    
    public Iterator<Message> iterator() {
        return messages.iterator();
    }

    public void setListener(MessageListener listener) {
        this.listener = listener;
    }
}
