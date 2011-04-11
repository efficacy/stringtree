package org.stringtree.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class StreamUtils {
    
    public static BufferedInputStream ensureBuffered(InputStream in) {
        return isBuffered(in) ? (BufferedInputStream) in
                : new BufferedInputStream(in);
    }

    private static boolean isBuffered(InputStream in) {
        return in instanceof BufferedInputStream;
    }

    public static DataInputStream ensureData(InputStream in) {
        return isData(in) ? (DataInputStream) in : new DataInputStream(in);
    }

    private static boolean isPrint(OutputStream out) {
        return out instanceof PrintStream;
    }

    public static PrintStream ensurePrint(OutputStream out) {
        return isPrint(out) ? (PrintStream) out : new PrintStream(out);
    }

    private static boolean isData(InputStream in) {
        return in instanceof DataInputStream;
    }

    public static void copyStream(InputStream in, OutputStream out,
            boolean autoclose) throws IOException {
        in = ensureBuffered(in);

        for (int c = in.read(); c >= 0; c = in.read()) {
            out.write(c);
        }

        out.flush();

        if (autoclose) {
            in.close();
        }
    }

    public static void copyStream(InputStream in, OutputStream out)
            throws IOException {
        copyStream(in, out, true);
    }

    public static byte[] readStreamBytes(InputStream in, boolean autoclose) throws IOException {
        if (in == null) throw new IOException("null input stream");
        ByteArrayOutputStream ret = new ByteArrayOutputStream();

        try {
            in = ensureBuffered(in);

            for (int c = in.read(); c != -1; c = in.read()) {
                ret.write(c);
            }
        } finally {
            if (autoclose) {
                in.close();
            }
        }

        return ret.toByteArray();
    }

    static class Stepper {
    	public int sofar;
    	public int max;
    	public Stepper(int max) {
    		this.sofar = 0;
    		this.max = max;
    	}
    }
    
    public static String readStream(InputStream in, int len, boolean autoclose) throws IOException {
    	Stepper stepper = new Stepper(len);
        if (in == null) throw new IOException("null input stream");
        StringBuffer ret = new StringBuffer();

        try {
            in = ensureBuffered(in);
            int c = readNext(in, stepper);
            while ( -1 != c ) {
                ret.append((char) c);
                c = readNext(in, stepper);
            }
        } finally {
            if (autoclose) {
                in.close();
            }
        }

        return ret.toString();
    }

	public static int readNext(InputStream in, Stepper stepper) throws IOException {
		if (stepper.sofar >= stepper.max) return -1;
		++stepper.sofar;
		return in.read();
	}
    
    public static String readLine(InputStream in, EndOfTheLine eol) throws IOException {
        if (in == null) throw new IOException("null input stream");
        StringBuffer ret = new StringBuffer();

        for (int c = in.read(); c != -1 && !eol.match(c); c = in.read()) {
            ret.append((char) c);
        }

        return ret.toString();
    }
    
    public static String readLine(InputStream in) throws IOException {
        return readLine(in, EndOfTheLine.CRLF());
    }

    public static String readStream(InputStream in, boolean autoclose) throws IOException {
        return readStream(in, Integer.MAX_VALUE, autoclose);
    }

    public static String readStream(InputStream in) throws IOException {
        return readStream(in, true);
    }

    public static String readStream(InputStream in, int len) throws IOException {
        return readStream(in, len, true);
    }

    public static void closeInput(InputStream stream) {
        try {
            if (stream != null)
                stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeOutput(OutputStream stream) {
        try {
            if (stream != null)
                stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static InputStream stringStream(String string) {
        return new ByteArrayInputStream(string.getBytes());
    }
}
