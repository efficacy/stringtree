package org.stringtree.jms;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueSender;

public class InMemoryQueueSender extends InMemoryMessageProducer implements QueueSender {

    public InMemoryQueueSender(Queue queue, boolean autostart) {
        super(queue, autostart);
    }

    public Queue getQueue() {
        return (Queue)getDestination();
    }

    public void send(Queue queue, Message message) {
        sendOrDefer(queue, message);
    }

    public void send(Queue queue, Message message, int arg2, int arg3, long arg4) {
        sendOrDefer(queue, message);
    }

    public void close() {
    }
}
