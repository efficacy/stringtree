package org.stringtree.util.testing;

import java.io.IOException;
import java.io.InputStream;

public class DiagnosticInputStream extends InputStream {

    private InputStream real;

    public DiagnosticInputStream(InputStream real) {
        this.real = real;
    }

    public int read() throws IOException {
        int c = real.read();
        System.err.println("DiagnosticInputStream read got " + c + "(" + (char)c + ")");
        return c;
    }

}
