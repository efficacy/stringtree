package org.stringtree.jms;

import java.util.Enumeration;

import javax.jms.Queue;
import javax.jms.QueueBrowser;

import org.stringtree.util.enumeration.IteratorEnumeration;

public class InMemoryQueueBrowser implements QueueBrowser {
    
    private InMemoryQueue queue;
    private String selector;

    public InMemoryQueueBrowser(Queue queue, String selector) {
        this.queue = (InMemoryQueue) queue;
        this.selector = selector;
    }

    public InMemoryQueueBrowser(Queue queue) {
        this(queue, null);
    }

    public void close() {
    }

	@SuppressWarnings("rawtypes")
	public Enumeration getEnumeration() {
        return new IteratorEnumeration(queue.iterator());
    }

    public String getMessageSelector() {
        return selector;
    }

    public Queue getQueue() {
        return queue;
    }
}
