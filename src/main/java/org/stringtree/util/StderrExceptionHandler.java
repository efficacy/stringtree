package org.stringtree.util;

public class StderrExceptionHandler implements ExceptionHandler {
    public void handle(Exception e) {
        e.printStackTrace(System.err);
    }

    public void handle(Exception e, String contextMessage) {
        System.err.println("Error during " + contextMessage);
        e.printStackTrace(System.err);
    }

    public static final StderrExceptionHandler it = new StderrExceptionHandler();
}
