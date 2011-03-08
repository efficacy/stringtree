package org.stringtree.jms.message;

import java.util.Properties;

import javax.jms.TextMessage;

import org.stringtree.util.Utils;

public class InMemoryTextMessage extends InMemoryMessage implements TextMessage {

    private String text;

    public InMemoryTextMessage(String text) {
        super(new Properties());
        setText(text);
    }

    public InMemoryTextMessage() {
        this(null);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Object clone() {
        return super.clone();
    }

    public void clearBody() {
        text = null;
    }
    
    public String toString() {
        return super.toString() + "(" + text + ")";
    }
    
    public int hashCode() {
    	return ("$" + text + "$").hashCode();
    }
    
    public boolean equals(Object obj) {
        return obj instanceof InMemoryTextMessage && Utils.same(text, ((InMemoryTextMessage)obj).text);
    }
}
