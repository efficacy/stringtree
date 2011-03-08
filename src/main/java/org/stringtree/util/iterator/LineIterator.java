package org.stringtree.util.iterator;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.File;
import java.io.FileReader;

/**
 * An Iterator to iterate through the "lines" of a stream or reader.
 * 
 * Typically used with a FileReader or InputStreamReader
 * 
 * @author Frank Carver
 */
public class LineIterator extends ReaderLineIterator {
    
    public LineIterator(Reader in, boolean autoclose) {
        super(in, autoclose);
    }

    public LineIterator(Reader in) {
        super(in);
    }

    public LineIterator(File in, boolean autoclose) throws FileNotFoundException {
        super(new FileReader(in), autoclose);
    }

    public LineIterator(File in) throws FileNotFoundException {
        super(new FileReader(in));
    }
    
    protected String getLine() {
        return line;
    }

    public String nextLine() {
        return next();
    }
}
