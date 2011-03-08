package org.stringtree.util;

public interface ExceptionHandler {
    void handle(Exception e);
    void handle(Exception e, String contextMessage);
}
