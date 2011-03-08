package org.stringtree.jms.message;

import java.io.Serializable;

import javax.jms.ObjectMessage;


public class InMemoryObjectMessage extends InMemoryMessage implements ObjectMessage {

    private Serializable payload;
    
    public InMemoryObjectMessage(Serializable obj) {
        setObject(obj);
    }

    public InMemoryObjectMessage() {
        this(null);
    }

    public Serializable getObject() {
        return payload;
    }

    public void setObject(Serializable obj) {
        this.payload = obj;
    }
}
