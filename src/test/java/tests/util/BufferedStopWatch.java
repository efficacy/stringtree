package tests.util;

import java.io.Writer;
import java.io.IOException;

import org.stringtree.timing.StopWatch;

public class BufferedStopWatch extends StopWatch {
    private Writer out = null;

    public BufferedStopWatch(Writer out) {
        this.out = out;
    }

    public void reset() {
        flush();
        super.reset();
    }

    public void flush() {
        if (out != null) {
            try {
                out.write("total: " + totalSeconds() + "\n");
                out.flush();
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
    
    protected void emit(String s) {
        try {
            out.write(s);
            out.write("\n");
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
