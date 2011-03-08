package org.stringtree.jms;

import javax.jms.Topic;
import javax.jms.TopicSubscriber;

public class InMemoryTopicSubscriber extends InMemoryMessageConsumer implements TopicSubscriber {

    private InMemoryTopic topic;
    private boolean noLocal;
    
    public InMemoryTopicSubscriber(Topic topic, String selector, boolean noLocal) {
        super(selector);
        this.topic = (InMemoryTopic) topic;
        this.noLocal = noLocal;
        this.topic.subscribe(this);
    }
    
    public InMemoryTopicSubscriber(Topic topic, String selector) {
        this(topic, selector, false);
    }
    
    public InMemoryTopicSubscriber(Topic topic) {
        this(topic, null, false);
    }

    public boolean getNoLocal() {
        return noLocal;
    }

    public Topic getTopic() {
        return topic;
    }
}
