package org.stringtree.jms;

import javax.jms.ConnectionConsumer;
import javax.jms.ServerSessionPool;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;

public class InMemoryTopicConnection extends InMemoryConnection implements TopicConnection {

    public InMemoryTopicConnection(boolean autostart) {
        super(autostart);
    }

    public InMemoryTopicConnection(String userName, String password, boolean autostart) {
        super(autostart);
    }

    public ConnectionConsumer createConnectionConsumer(Topic topic, String selector,
            ServerSessionPool pool, int maxMessages) {
    	return super.createConnectionConsumer(topic, selector, pool, maxMessages);
    }

    public TopicSession createTopicSession(boolean arg0, int arg1) {
        InMemoryTopicSession ret = new InMemoryTopicSession(autostart);
        sessions.add(ret);
        return ret;
    }
}
