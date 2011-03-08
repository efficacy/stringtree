package org.stringtree.template;

import java.io.PrintStream;

public interface StringCollector {
    void write(char cc);
    void write(String value);
    void write(Object value);
    void write(byte[] bytes);
    void flush();
    int length();
    PrintStream printStream();
}
