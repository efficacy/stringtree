package org.stringtree.jms.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MessageNotReadableException;
import javax.jms.MessageNotWriteableException;

public class InMemoryBytesMessage extends InMemoryMessage implements BytesMessage {
	private static final int WRITE = 0;
	private static final int READ = 1;
	
	private ByteArrayOutputStream data;
	private DataOutputStream out;
	private DataInputStream in;
	private int mode;
	
	public InMemoryBytesMessage() {
		data = new ByteArrayOutputStream();
		out = null;
		in = null;
		mode = WRITE;
	}
	
	private void ensureReadMode() throws JMSException {
		if (mode != READ) throw new MessageNotReadableException("attempt to read from message in write mode, use 'reset()' to switch");
	}
	
	private void ensureWriteMode() throws JMSException {
		if (mode != WRITE) throw new MessageNotWriteableException("attempt to write to message in read mode, use 'clearBody()' to switch");
	}

	@Override
    public void reset() throws JMSException {
    	mode = READ;
    	out = null;
    	in = new DataInputStream(new ByteArrayInputStream(data.toByteArray()));
    }

	@Override
    public void clearBody() {
    	mode = WRITE;
    	data.reset();
    	out = new DataOutputStream(data);
    	in = null;
    }

	@Override
    public boolean readBoolean() throws JMSException {
    	ensureReadMode();
    	try {
			return in.readBoolean();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public byte readByte() throws JMSException {
    	ensureReadMode();
    	try {
			return in.readByte();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public int readBytes(byte[] arg0) throws JMSException {
    	ensureReadMode();
    	try {
			return in.read(arg0);
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public int readBytes(byte[] arg0, int arg1) throws JMSException {
    	ensureReadMode();
    	try {
			return in.read(arg0, 0, arg1);
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public char readChar() throws JMSException {
    	ensureReadMode();
    	try {
			return in.readChar();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public double readDouble() throws JMSException {
    	ensureReadMode();
    	try {
			return in.readDouble();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public float readFloat() throws JMSException {
    	ensureReadMode();
    	try {
			return in.readFloat();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public int readInt() throws JMSException {
    	ensureReadMode();
    	try {
			return in.readInt();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public long readLong() throws JMSException {
    	ensureReadMode();
    	try {
			return in.readLong();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public short readShort() throws JMSException {
    	ensureReadMode();
    	try {
			return in.readShort();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public String readUTF() throws JMSException {
    	ensureReadMode();
    	try {
			return in.readUTF();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public int readUnsignedByte() throws JMSException {
    	ensureReadMode();
    	try {
			return in.readUnsignedByte();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public int readUnsignedShort() throws JMSException {
    	ensureReadMode();
    	try {
			return in.readUnsignedShort();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public void writeBoolean(boolean arg0) throws JMSException {
    	ensureWriteMode();
    	try {
			out.writeBoolean(arg0);
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public void writeByte(byte arg0) throws JMSException {
    	ensureWriteMode();
    	try {
			out.writeByte(arg0);
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public void writeBytes(byte[] arg0) throws JMSException {
    	ensureWriteMode();
    	try {
			out.write(arg0);
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public void writeBytes(byte[] arg0, int arg1, int arg2) throws JMSException {
    	ensureWriteMode();
    	try {
			out.write(arg0, arg1, arg2);
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public void writeChar(char arg0) throws JMSException {
    	ensureWriteMode();
    	try {
			out.writeChar(arg0);
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public void writeDouble(double arg0) throws JMSException {
    	ensureWriteMode();
    	try {
			out.writeDouble(arg0);
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public void writeFloat(float arg0) throws JMSException {
    	ensureWriteMode();
    	try {
			out.writeFloat(arg0);
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public void writeInt(int arg0) throws JMSException {
    	ensureWriteMode();
    	try {
			out.writeInt(arg0);
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public void writeLong(long arg0) throws JMSException {
    	ensureWriteMode();
    	try {
			out.writeLong(arg0);
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public void writeObject(Object arg0) throws JMSException {
    	if (null == arg0) throw new JMSException("attempt to write null object to message");
    	else if (arg0 instanceof Boolean) writeBoolean((Boolean)arg0);
    	else if (arg0 instanceof Byte) writeByte((Byte)arg0);
    	else if (arg0 instanceof byte[]) writeBytes((byte[])arg0);
    	else if (arg0 instanceof Character) writeChar((Character)arg0);
    	else if (arg0 instanceof Double) writeDouble((Double)arg0);
    	else if (arg0 instanceof Float) writeFloat((Float)arg0);
    	else if (arg0 instanceof Integer) writeInt((Integer)arg0);
    	else if (arg0 instanceof Long) writeLong((Long)arg0);
    	else if (arg0 instanceof Short) writeShort((Short)arg0);
    	else if (arg0 instanceof String) writeUTF((String)arg0);
    	else throw new JMSException("attempt to write unknown type " + arg0.getClass().getName() + " to message");
    }

	@Override
    public void writeShort(short arg0) throws JMSException {
    	ensureWriteMode();
    	try {
			out.writeShort(arg0);
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	@Override
    public void writeUTF(String arg0) throws JMSException {
    	ensureWriteMode();
    	try {
			out.writeUTF(arg0);
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
    }

	public long getBodyLength() throws JMSException {
		return data.size();
	}

}
