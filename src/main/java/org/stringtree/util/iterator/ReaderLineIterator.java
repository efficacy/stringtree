package org.stringtree.util.iterator;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.IOException;

import org.stringtree.util.ReaderUtils;

/**
 * An Iterator to iterate through the "lines" of a reader.
 * 
 * @author Frank Carver
 */
public class ReaderLineIterator extends AbstractLineIterator {
    
    protected BufferedReader in = null;

    public ReaderLineIterator(Reader in, boolean autoclose) {
        super(autoclose);
        this.in = ReaderUtils.makeBuffered(in);
    }

    public ReaderLineIterator(Reader in) {
        this(in, true);
    }

    protected String readLine() throws IOException {
        String ret = in.readLine();
        return ret;
    }

    protected void close() {
        ReaderUtils.close(in);
    }
}
