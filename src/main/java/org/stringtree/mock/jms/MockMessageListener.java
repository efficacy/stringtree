package org.stringtree.mock.jms;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.stringtree.mock.RecordingMock;

public class MockMessageListener extends RecordingMock implements MessageListener {

    public void onMessage(Message message) {
        record(this, "onMessage", message);
    }

}
