package tests;

import java.util.Arrays;

import junit.framework.Assert;

import org.stringtree.mock.MockAction;
import org.stringtree.mock.RecordingMock;

public class AssertingMock extends RecordingMock {

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
