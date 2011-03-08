package org.stringtree.jms;

import javax.jms.ConnectionConsumer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.ServerSessionPool;

public class InMemoryQueueConnection extends InMemoryConnection implements QueueConnection {

    public InMemoryQueueConnection(boolean autostart) {
        super(autostart);
    }

    public InMemoryQueueConnection(String userName, String password, boolean autostart) {
        super(autostart);
    }

    public ConnectionConsumer createConnectionConsumer(Queue queue, String selector,
            ServerSessionPool pool, int maxMessages) {
        return new InMemoryConnectionConsumer(pool);
    }

    public QueueSession createQueueSession(boolean transacted, int acknowledgeMode) {
        InMemoryQueueSession ret = new InMemoryQueueSession(transacted, acknowledgeMode, autostart);
        sessions.add(ret);
        return ret;
    }

}
