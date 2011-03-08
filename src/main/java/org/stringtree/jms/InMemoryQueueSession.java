package org.stringtree.jms;

import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;

public class InMemoryQueueSession extends InMemorySession implements QueueSession {

    public InMemoryQueueSession(boolean transacted, int acknowledgeMode, boolean autostart) {
        super(transacted, acknowledgeMode, autostart);
    }

    public QueueReceiver createReceiver(Queue queue) {
        InMemoryQueueReciever ret = new InMemoryQueueReciever(queue, autostart);
        consumers.add(ret);
        return ret;
    }

    public QueueReceiver createReceiver(Queue queue, String name) {
        InMemoryQueueReciever ret = new InMemoryQueueReciever(queue, name, autostart);
        consumers.add(ret);
        return ret;
    }

    public QueueSender createSender(Queue queue) {
        InMemoryQueueSender ret = new InMemoryQueueSender(queue, autostart);
        producers.add(ret);
        return ret;
    }
}
