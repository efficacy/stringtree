package org.stringtree.jms.message;

import javax.jms.JMSException;
import javax.jms.StreamMessage;

public class InMemoryStreamMessage extends InMemoryBytesMessage implements StreamMessage {

	// TODO what should this actually do?
	@Override
	public Object readObject() throws JMSException {
		throw new JMSException("unimplemented readObject method");
	}

	@Override
	public String readString() throws JMSException {
		return readUTF();
	}

	@Override
	public void writeString(String arg0) throws JMSException {
		writeUTF(arg0);
	}
}
