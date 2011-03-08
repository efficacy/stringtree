package org.stringtree.util.spec;

public class HandlerDefinition {
    
    public String pattern;
    public Handler handler;

    public HandlerDefinition(String pattern, Handler handler) {
        this.pattern = pattern;
        this.handler = handler;
    }
}
