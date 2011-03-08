package org.stringtree.util.logging;

public interface Logger {
    static final Logger logger = new StreamLogger(System.err);
    static final CategoryLogger catlogger = new CategoryLogger(logger);
    void logPart(String text);
    void log(String text);
}
