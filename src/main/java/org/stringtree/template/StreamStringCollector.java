package org.stringtree.template;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.stringtree.util.StreamUtils;

public class StreamStringCollector implements StringCollector {

    private PrintStream ps;

    public StreamStringCollector(OutputStream stream) {
        this.ps = StreamUtils.ensurePrint(stream);
    }

    public void write(char cc) {
        ps.print(cc);
    }

    public void write(String value) {
        ps.print(value);
    }

    public void write(byte[] bytes) {
        try {
            ps.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(Object value) {
        ps.print(value);
    }

    public void flush() {
        ps.flush();
    }

    public int length() {
        return 0;
    }

    public PrintStream printStream() {
        return ps;
    }
}
