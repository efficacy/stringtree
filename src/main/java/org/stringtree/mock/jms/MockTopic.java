package org.stringtree.mock.jms;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.stringtree.jms.InMemoryTopic;
import org.stringtree.mock.RecordingMock;

public class MockTopic extends InMemoryTopic {
	
    public RecordingMock recorder;
	
	public MockTopic(String name) {
	    super(name);
        ensureRecorder();
	}

    private void record(String message, Object... args) {
        ensureRecorder();
        recorder.record(message, args);
    }

    protected void ensureRecorder() {
        if (null == recorder) recorder = new RecordingMock(this);
    }
	
	public void sendMessage(Message message) {
	    record("sendMessage", message);
	    super.sendMessage(message);
	}

	public String getTopicName() {
	    record("getTopicName");
		return super.getTopicName();
	}

    public void subscribe(MessageListener listener) {
        record("subscribe", listener);
        super.subscribe(listener);
    }

    public void unsubscribe(MessageListener listener) {
        record("subscribe", listener);
        super.unsubscribe(listener);
    }

    public void reset() {
        record("getTopicName");
        super.reset();
    }

    public int size() {
        record("getTopicName");
        return super.size();
    }

	public void delete() {
	    record("delete");
	    super.delete();
	}
}
