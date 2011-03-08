package org.stringtree.jms;

import javax.jms.Message;

public class DeferredMessage<T> {
    
    public T destination;
    public Message message;

    public DeferredMessage(T destination, Message message) {
        this.destination = destination;
        this.message = message;
    }
}
