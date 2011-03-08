package org.stringtree.util;

public class Status {
    
    private int status;

    public Status(int dfl) {
        this.status = dfl;
    }

    public synchronized int get() {
        return status;
    }

    public synchronized void set(int status) {
        this.status = status;
    }
}
