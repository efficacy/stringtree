package org.stringtree.util.logging;

import org.stringtree.util.Delegator;

public class DelegatedLogger extends Delegator implements Logger {
    
    public DelegatedLogger(Logger other) {
        super(other);
    }

    public void logPart(String text) {
        ((Logger) getOther()).logPart(text);
    }

    public void log(String text) {
        ((Logger) getOther()).log(text);
    }
}
