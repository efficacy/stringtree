package org.stringtree.mock.jms;

import javax.jms.Connection;
import javax.jms.QueueConnection;
import javax.jms.TopicConnection;

import org.stringtree.jms.InMemoryConnectionFactory;
import org.stringtree.mock.RecordingMock;

public class MockConnectionFactory extends InMemoryConnectionFactory {

    public RecordingMock recorder = new RecordingMock(); 
    
    public MockConnectionFactory(boolean autostart) {
        super(autostart);
    }

    private void record(String message, Object... args) {
        if (null == recorder) recorder = new RecordingMock(this);
        recorder.record(message, args);
    }

    public Connection createConnection() {
		record("createConnection");
		return super.createConnection();
	}

	public Connection createConnection(String userName, String password) {
	    record("createConnection", userName, password);
		return super.createConnection(userName, password);
	}

	public QueueConnection createQueueConnection() {
	    record("createQueueConnection");
		return super.createQueueConnection();
	}

	public QueueConnection createQueueConnection(String userName, String password) { 
	    record("createQueueConnection", userName, password);
		return super.createQueueConnection(userName, password);
	}

	public TopicConnection createTopicConnection() {
	    record("createTopicConnection");
		return super.createTopicConnection();
	}

	public TopicConnection createTopicConnection(String userName, String password) {
	    record("createTopicConnection", userName, password);
		return super.createTopicConnection(userName, password);
	}
	
	public RecordingMock getRecorder() {
	    return recorder;
	}
}
