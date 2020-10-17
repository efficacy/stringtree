package org.stringtree.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

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

    public void assertCalled(int nTimes, String message, Object... args) {
        int count = 0;
        for (MockAction action : recorded) {
            if (action.message.equals(message) && Arrays.equals(args, action.arguments)) {
                ++count;
            }
        }
        Assert.assertEquals(nTimes, count);
    }

    public void assertCalled(String message, Object... args) {
    	assertCalled(1, message, args);
    }

    public void assertNotCalled(String message, Object... args) {
        assertCalled(0, message, args);
    }

    public void assertCalled(int nTimes, String message) {
        int count = 0;
        for (MockAction action : recorded) {
            if (action.message.equals(message)) {
                ++count;
            }
        }
        Assert.assertEquals(nTimes, count);
    }

    public void assertCalled(String message) {
        assertCalled(1, message);
    }

    public void assertNotCalled(String message) {
        assertCalled(0, message);
    }
}
