package org.stringtree.util.logging;

import java.io.OutputStream;
import java.io.PrintStream;

import org.stringtree.util.StreamUtils;

public class StreamLogger implements Logger {
    
    protected PrintStream out;

    public StreamLogger(OutputStream out) {
        this.out = StreamUtils.ensurePrint(out);
    }

    public void logPart(String text) {
        out.print(text);
    }

    public void log(String text) {
        out.println(text);
    }
}
