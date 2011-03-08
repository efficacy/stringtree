package org.stringtree.jms;

import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

public class InMemoryTopicSession extends InMemorySession implements TopicSession {

    public InMemoryTopicSession(boolean autostart) {
        super(autostart);
    }

    public TopicSubscriber createDurableSubscriber(Topic topic, String selector) {
    	return super.createDurableSubscriber(topic, selector);
    }

    public TopicSubscriber createDurableSubscriber(Topic topic, String selector,
            String arg2, boolean arg3) {
    	return super.createDurableSubscriber(topic, selector, arg2, arg3);
    }

    public TopicPublisher createPublisher(Topic topic) {
        InMemoryTopicPublisher ret = new InMemoryTopicPublisher(topic, autostart);
        producers.add(ret);
        return ret;
    }

    public TopicSubscriber createSubscriber(Topic topic) {
        InMemoryTopicSubscriber ret = new InMemoryTopicSubscriber(topic);
        ret.setRunning(autostart);
        consumers.add(ret);
        return ret;
    }

    public TopicSubscriber createSubscriber(Topic topic, String selector, boolean arg2) {
        InMemoryTopicSubscriber ret = new InMemoryTopicSubscriber(topic, selector, autostart);
        consumers.add(ret);
        return ret;
    }
}