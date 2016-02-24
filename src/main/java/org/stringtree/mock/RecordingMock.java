package org.stringtree.mock;

import java.util.ArrayList;
import java.util.List;

public class RecordingMock {
    
    public boolean verbose = false;
    public List<MockAction> recorded = new ArrayList<MockAction>();
    public Throwable exception;
    public Object target;
    
    public RecordingMock(Object target) {
        this.target = target;
    }
    
    public RecordingMock() {
        this(null);
    }

    public void record(MockAction action) {
        if (verbose) System.out.println(action);
        recorded.add(action);
    }
    
    public void clear() {
        recorded.clear();
    }

    public void record(Object destination, String message, Object... arguments) {
        record(new MockAction(destination, message, arguments));
    }

    public void record(Object destination, String message) {
        record(new MockAction(destination, message));
    }

    public void record(String message, Object... arguments) {
        record(new MockAction(target, message, arguments));
    }

    public void record(String message) {
        record(new MockAction(target, message));
    }
    
    public void setException(Throwable throwable) {
        this.exception = throwable;
    }
}
