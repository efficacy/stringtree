package org.stringtree.jms;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;

public class InMemoryConnection implements Connection {

    private String clientID;
    private ExceptionListener exceptionListener;
    private ConnectionMetaData metadata = new InMemoryConnectionMetaData();
    
    protected boolean autostart;
    protected List<InMemorySession> sessions = new ArrayList<InMemorySession>();
    
    public InMemoryConnection(boolean autostart) {
        this.autostart = autostart;
    }

    public void close() {
    }

    public String getClientID() {
        return clientID;
    }

    public ExceptionListener getExceptionListener() {
        return exceptionListener;
    }

    public ConnectionMetaData getMetaData() {
        return metadata;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public void setExceptionListener(ExceptionListener exceptionListener) {
        this.exceptionListener = exceptionListener;
    }

    public void start() {
        updateSessions(true);
    }

    public void stop() {
        updateSessions(false);
    }

    private void updateSessions(boolean running) {
        for (InMemorySession session : sessions) {
            session.setRunning(running);
        }
    }

    public ConnectionConsumer createConnectionConsumer(Destination dest, String selector,
            ServerSessionPool pool, int maxMessages) {
        return new InMemoryConnectionConsumer(pool);
    }

    public ConnectionConsumer createDurableConnectionConsumer(Topic topic,
            String selector, String arg2, ServerSessionPool pool, int maxMessages) {
        return new InMemoryConnectionConsumer(pool);
    }

    public Session createSession(boolean transacted, int acknowledgeMode) {
        InMemorySession ret = new InMemorySession(transacted, acknowledgeMode, autostart);
        sessions.add(ret);
        return ret;
    }
}
