package org.stringtree.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class WriterUtils {
    
    public static PrintWriter ensurePrint(Writer out) {
        return isPrint(out) ? (PrintWriter) out : new PrintWriter(out);
    }

    private static boolean isPrint(Writer out) {
        return out instanceof PrintWriter;
    }

    public static void close(Writer out) {
        try {
            if (out != null)
                out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
