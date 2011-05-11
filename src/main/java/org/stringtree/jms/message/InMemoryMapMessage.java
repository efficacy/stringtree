package org.stringtree.jms.message;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.MapMessage;

import org.stringtree.util.enumeration.IteratorEnumeration;

public class InMemoryMapMessage extends InMemoryMessage implements MapMessage {

    private Map<String, Object> payload;
    
    public InMemoryMapMessage() {
        payload = new HashMap<String, Object>();
    }
    
    public boolean getBoolean(String key) {
        return (Boolean)payload.get(key);
    }

    public byte getByte(String key) {
        return (Byte)payload.get(key);
    }

    public byte[] getBytes(String key) {
        return (byte[])payload.get(key);
    }

    public char getChar(String key) {
        return (Character)payload.get(key);
    }

    public double getDouble(String key) {
        return (Double)payload.get(key);
    }

    public float getFloat(String key) {
        return (Float)payload.get(key);
    }

    public int getInt(String key) {
        return (Integer)payload.get(key);
    }

    public long getLong(String key) {
        return (Long)payload.get(key);
    }

	@SuppressWarnings("rawtypes")
	public Enumeration getMapNames() {
        return new IteratorEnumeration(payload.keySet().iterator());
    }

    public Object getObject(String key) {
        return payload.get(key);
    }

    public short getShort(String key) {
        return (Short)payload.get(key);
    }

    public String getString(String key) {
        return (String)payload.get(key);
    }

    public boolean itemExists(String key) {
        return payload.containsKey(key);
    }

    public void setBoolean(String key, boolean value) {
        payload.put(key, value);
    }

    public void setByte(String key, byte value) {
        payload.put(key, value);
    }

    public void setBytes(String key, byte[] value) {
        payload.put(key, value);
    }

    public void setBytes(String key, byte[] source, int offset, int length) {
        byte[] value = new byte[length];
        for (int i = 0; i < length; ++i) {
            value[i] = source[offset+i];
        }
        payload.put(key, value);
    }

    public void setChar(String key, char value) {
        payload.put(key, value);
    }

    public void setDouble(String key, double value) {
        payload.put(key, value);
    }

    public void setFloat(String key, float value) {
        payload.put(key, value);
    }

    public void setInt(String key, int value) {
        payload.put(key, value);
    }

    public void setLong(String key, long value) {
        payload.put(key, value);
    }

    public void setObject(String key, Object value) {
        payload.put(key, value);
    }

    public void setShort(String key, short value) {
        payload.put(key, value);
    }

    public void setString(String key, String value) {
        payload.put(key, value);
    }
}
