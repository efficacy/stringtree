package org.stringtree.util;

public class StdoutExceptionHandler implements ExceptionHandler {
    public void handle(Exception e) {
        e.printStackTrace(System.out);
    }

    public void handle(Exception e, String contextMessage) {
        System.out.println("Error during " + contextMessage);
        e.printStackTrace(System.out);
    }

    public static final StdoutExceptionHandler it = new StdoutExceptionHandler();
}
