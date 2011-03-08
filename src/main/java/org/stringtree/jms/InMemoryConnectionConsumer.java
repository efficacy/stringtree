package org.stringtree.jms;

import javax.jms.ConnectionConsumer;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;

public class InMemoryConnectionConsumer implements ConnectionConsumer {
    
    ServerSessionPool pool;

    public InMemoryConnectionConsumer(ServerSessionPool pool) {
        this.pool = pool;
    }

    public void close() throws JMSException {
    }

    public ServerSessionPool getServerSessionPool() throws JMSException {
        return pool;
    }

}
