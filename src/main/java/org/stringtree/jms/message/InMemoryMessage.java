package org.stringtree.jms.message;

import java.util.Enumeration;
import java.util.Properties;

import javax.jms.Destination;
import javax.jms.Message;

import org.stringtree.util.BooleanUtils;
import org.stringtree.util.FloatNumberUtils;
import org.stringtree.util.IntegerNumberUtils;
import org.stringtree.util.LongNumberUtils;
import org.stringtree.util.SmallNumberUtils;

public class InMemoryMessage implements Message, Cloneable {

    private Properties properties;
    private String messageID;
    private long timestamp;
    private String correlationID;
    private byte[] correlationIDBytes;
    private Destination replyTo;
    private Destination destination;
    private int deliveryMode;
    private boolean redelivered;
    private String type;
    private long expiration;
    private int priority;
    
    protected InMemoryMessage(Properties properties) {
        this.properties = properties;
    }
    
    public InMemoryMessage() {
        this(new Properties());
    }

    public String getJMSMessageID() {
        return messageID;
    }

    public void setJMSMessageID(String messageID) {
        this.messageID = messageID;
    }

    public long getJMSTimestamp() {
        return timestamp;
    }

    public void setJMSTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getJMSCorrelationIDAsBytes() {
        return correlationIDBytes;
    }

    public void setJMSCorrelationIDAsBytes(byte[] correlationIDBytes)
            {
        this.correlationIDBytes = correlationIDBytes;
    }

    public void setJMSCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }

    public String getJMSCorrelationID() {
        return correlationID;
    }

    public Destination getJMSReplyTo() {
        return replyTo;
    }

    public void setJMSReplyTo(Destination replyTo) {
        this.replyTo = replyTo;
    }

    public Destination getJMSDestination() {
        return destination;
    }

    public void setJMSDestination(Destination destination) {
        this.destination = destination;
    }

    public int getJMSDeliveryMode() {
        return deliveryMode;
    }

    public void setJMSDeliveryMode(int deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public boolean getJMSRedelivered() {
        return redelivered;
    }

    public void setJMSRedelivered(boolean redelivered) {
        this.redelivered = redelivered;
    }

    public String getJMSType() {
        return type;
    }

    public void setJMSType(String type) {
        this.type = type;
    }

    public long getJMSExpiration() {
        return expiration;
    }

    public void setJMSExpiration(long expiration) {
        this.expiration = expiration;
    }

    public int getJMSPriority() {
        return priority;
    }

    public void setJMSPriority(int priority) {
        this.priority = priority;
    }

    public void clearProperties() {
        properties.clear();
    }

    public boolean propertyExists(String name) {
        return properties.containsKey(name);
    }

    public boolean getBooleanProperty(String name) {
        return BooleanUtils.booleanValue(properties.get(name));
    }

    public byte getByteProperty(String name) {
        return SmallNumberUtils.byteValue(properties.get(name));
    }

    public short getShortProperty(String name) {
        return SmallNumberUtils.shortValue(properties.get(name));
    }

    public int getIntProperty(String name) {
        return IntegerNumberUtils.intValue(properties.get(name));
    }

    public long getLongProperty(String name) {
        return LongNumberUtils.longValue(properties.get(name));
    }

    public float getFloatProperty(String name) {
        return FloatNumberUtils.floatValue(properties.get(name));
    }

    public double getDoubleProperty(String name) {
        return FloatNumberUtils.doubleValue(properties.get(name));
    }

    public String getStringProperty(String name) {
        return properties.getProperty(name);
    }

    public Object getObjectProperty(String name) {
        return properties.get(name);
    }

    @SuppressWarnings("unchecked")
	public Enumeration getPropertyNames() {
        return properties.propertyNames();
    }

    public void setBooleanProperty(String name, boolean value) {
        properties.put(name, Boolean.valueOf(value));
    }

    public void setByteProperty(String name, byte value) {
        properties.put(name, Byte.valueOf(value));
    }

    public void setShortProperty(String name, short value) {
        properties.put(name, Short.valueOf(value));
    }

    public void setIntProperty(String name, int value) {
        properties.put(name, Integer.valueOf(value));
    }

    public void setLongProperty(String name, long value) {
        properties.put(name, Long.valueOf(value));
    }

    public void setFloatProperty(String name, float value) {
        properties.put(name, Float.valueOf(value));
    }

    public void setDoubleProperty(String name, double value) {
        properties.put(name, Double.valueOf(value));
    }

    public void setStringProperty(String name, String value) {
        properties.setProperty(name, value);
    }

    public void setObjectProperty(String name, Object value) {
        properties.put(name, value);
    }

    public void acknowledge() {
    }

    public void clearBody() {
    }

    /**
     * copy the fields from this message into another freshly-created one
     */
    public InMemoryMessage cloneFields(InMemoryMessage message) {
        message.messageID = messageID;
        message.timestamp = timestamp;
        message.correlationID = correlationID;
        message.correlationIDBytes = correlationIDBytes;
        message.destination = destination;
        message.replyTo = replyTo;
        message.deliveryMode = deliveryMode;
        message.redelivered = redelivered;
        message.type = type;
        message.expiration = expiration;
        message.priority = priority;
        message.properties.putAll(properties); // TODO warning, only a shallow copy
        return message;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return cloneFields(new InMemoryMessage(new Properties())); 
        }
    }
}
