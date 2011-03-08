package org.stringtree.util;

import java.io.PrintStream;

public interface DumpLine {
    void line(Object key, Object value, String indent, StringBuffer out);
    void line(Object key, Object value, String indent, PrintStream out);
}
