package org.stringtree.jms;

import javax.jms.Destination;
import javax.jms.Message;

public abstract class InMemoryDestination implements Destination {

    protected String name;

    public InMemoryDestination(String name) {
        this.name = name;
    }

    protected String getName() {
        return name;
    }

    public void delete() {
    }

    public  void reset() {
    }
    
    public String toString() {
        return super.toString() + "(" + name + ")";
    }
    
    public abstract void sendMessage(Message message);
}
