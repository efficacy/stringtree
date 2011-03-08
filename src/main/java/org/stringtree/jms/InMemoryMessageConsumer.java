package org.stringtree.jms;

import java.util.LinkedList;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;

public class InMemoryMessageConsumer implements MessageConsumer, MessageListener {

    protected MessageListener listener;
    private String selector;
    
    private List<Message> pending;
    private boolean running;
    
    public InMemoryMessageConsumer(String selector, boolean running) {
        this.selector = selector;
        this.running = running;
        pending = new LinkedList<Message>();
    }
    
    public void setRunning(boolean running) {
        this.running = running;
        if (running) flush();
    }
    
    public InMemoryMessageConsumer(String selector) {
        this(selector, false);
    }

    public void close() {
    }

    public MessageListener getMessageListener() {
        return listener;
    }

    public String getMessageSelector() {
        return selector;
    }

    public void setMessageListener(MessageListener listener) {
        this.listener = listener;
    }

    public Message receive() {
        return pending.isEmpty() ? null : pending.remove(0);
    }

    public Message receive(long arg0) {
        return pending.isEmpty() ? null : pending.remove(0);
    }

    public Message receiveNoWait() {
        return pending.isEmpty() ? null : pending.remove(0);
    }

    public void onMessage(Message message) {
        if (null != listener && running) {
            listener.onMessage(message);
        } else {
            pending.add(message);
        }
    }
    
    private void flush() {
        while (!pending.isEmpty() && running && null != listener) {
            listener.onMessage(pending.remove(0));
        }
    }
}
