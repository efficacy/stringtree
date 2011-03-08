package org.stringtree.util.logging;

import java.io.PrintWriter;
import java.io.Writer;

import org.stringtree.util.WriterUtils;

public class WriterLogger implements Logger {
    
    protected PrintWriter out;

    public WriterLogger(Writer out) {
        this.out = WriterUtils.ensurePrint(out);
    }

    public void logPart(String text) {
        out.print(text);
    }

    public void log(String text) {
        out.println(text);
    }
}
