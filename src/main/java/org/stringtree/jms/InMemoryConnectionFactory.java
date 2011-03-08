package org.stringtree.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

public class InMemoryConnectionFactory implements ConnectionFactory,
        QueueConnectionFactory, TopicConnectionFactory {
    
    private boolean autostart;

    public InMemoryConnectionFactory(boolean autostart) {
        this.autostart = autostart;
    }
    
    public Connection createConnection() {
        return new InMemoryConnection(autostart);
    }

    public Connection createConnection(String userName, String password) {
        return null;
    }

    public QueueConnection createQueueConnection() {
        return new InMemoryQueueConnection(autostart);
    }

    public QueueConnection createQueueConnection(String userName, String password) {
        return new InMemoryQueueConnection(userName, password, autostart);
    }

    public TopicConnection createTopicConnection() {
        return new InMemoryTopicConnection(autostart);
    }

    public TopicConnection createTopicConnection(String userName, String password) {
        return new InMemoryTopicConnection(userName, password, autostart);
    }
}
