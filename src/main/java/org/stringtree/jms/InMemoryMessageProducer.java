package org.stringtree.jms;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;

public abstract class InMemoryMessageProducer implements MessageProducer {
    private int deliveryMode;
    private boolean disableMessageID;
    private boolean disableMessageTimestamp;
    private int priority;
    private long timeToLive;
    
    protected Destination destination;
    protected boolean running;

    protected List<DeferredMessage<Destination>> deferred;
    
    public InMemoryMessageProducer(Destination destination, boolean autostart) {
    	this.destination = destination;
        this.running = autostart;
        deferred = new ArrayList<DeferredMessage<Destination>>();
    }
    
    public InMemoryMessageProducer(Destination destination) {
        this(destination, false);
    }

    public void close() {
    }

    public int getDeliveryMode() {
        return deliveryMode;
    }

    public boolean getDisableMessageID() {
        return disableMessageID;
    }

    public boolean getDisableMessageTimestamp() {
        return disableMessageTimestamp;
    }

    public int getPriority() {
        return priority;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public void setDeliveryMode(int deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public void setDisableMessageID(boolean disableMessageID) {
        this.disableMessageID = disableMessageID;
    }

    public void setDisableMessageTimestamp(boolean disableMessageTimestamp) {
        this.disableMessageTimestamp = disableMessageTimestamp;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void setRunning(boolean running) {
        this.running = running;
        if (running) flush();
    }
    
    public Destination getDestination() {
    	return destination;
    }

    public void send(Destination dest, Message message) {
        sendOrDefer(dest, message);
    }

    public void send(Message message) {
        sendOrDefer(destination, message);
    }

    public void send(Destination dest, Message message, int arg1, int arg2, long arg3) {
        sendOrDefer(destination, message);
    }

    public void send(Message message, int arg1, int arg2, long arg3) {
        sendOrDefer(destination, message);
    }

    protected void sendOrDefer(Destination destination, Message message) {
        if (running) {
            sendMessage(destination, message);
        } else {
            deferred.add(new DeferredMessage<Destination>(destination, message));
        }
    }

    private void sendMessage(Destination destination, Message message) {
        ((InMemoryDestination)destination).sendMessage(message);
    }

    public void flush() {
        while (!deferred.isEmpty()) {
            DeferredMessage<Destination> defer = deferred.remove(0);
            sendMessage(defer.destination, defer.message);
        }
    }
}
