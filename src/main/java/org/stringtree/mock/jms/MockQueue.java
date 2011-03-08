package org.stringtree.mock.jms;

import java.util.List;

import javax.jms.Message;

import org.stringtree.jms.InMemoryQueue;
import org.stringtree.mock.RecordingMock;

public class MockQueue extends InMemoryQueue {
	
    public RecordingMock recorder;
	
	public MockQueue(String name) {
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

	public String getQueueName() {
		record("getQueueName");
		return super.getQueueName();
	}
	
	public void sendMessage(Message message) {
	    record("sendMessage", message);
	    super.sendMessage(message);
	}

	public void delete() {
	    record("delete");
	    super.delete();
	}

    public int size() {
        record("size");
        return super.size();
    }

    public Message peek() {
        record("peek");
        return super.peek();
    }

    public Message recieve() {
        record("receive");
        return super.recieve();
    }

    public void reset() {
        record("reset");
        super.reset();
    }
    
    public List<Message> messages() {
        return messages;
    }
}
