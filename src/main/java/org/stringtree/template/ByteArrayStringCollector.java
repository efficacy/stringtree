package org.stringtree.template;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.stringtree.util.StringUtils;

public class ByteArrayStringCollector implements StringCollector {

    private ByteArrayOutputStream buf;
    private PrintStream stream;
    private PrintWriter ps;

    public ByteArrayStringCollector() {
        buf = new ByteArrayOutputStream();
        stream = new PrintStream(buf);
        Writer out;
        try {
            out = new OutputStreamWriter(stream, "UTF-8");
            ps = new PrintWriter(out);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ps = new PrintWriter(stream);
        } 
    }

    public void write(char cc) {
        ps.print(cc);
    }

    public void write(String value) {
        ps.print(value);
    }

    public void write(byte[] bytes) {
        ps.flush();
        try {
            buf.write(bytes);
        } catch (IOException e) {
            // it makes no sense to get an IO Exception writing to a memory buffer
            // but it's part of the signature so we have to deal with it. Sigh.
            e.printStackTrace();
        }
    }

    public void write(Object value) {
        write(StringUtils.nullToEmpty(value));
    }

    public void flush() {
        ps.flush();
    }

    public int length() {
        ps.flush();
        return buf.size();
    }

    public PrintStream printStream() {
        return stream;
    }

    public byte[] toByteArray() {
        ps.flush();
        return buf.toByteArray();
    }

    public String toString() {
        ps.flush();
        String ret = "";
        try {
            ret = buf.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // UTF-8 should always be supported. Sigh.
            ret = buf.toString();
        }
        
        return ret;
    }
}
