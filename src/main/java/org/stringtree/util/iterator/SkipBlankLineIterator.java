package org.stringtree.util.iterator;

import java.io.IOException;
import java.io.Reader;

import org.stringtree.util.StringUtils;

/**
 * An Iterator to iterate through the "lines" of a stream or reader, skipping
 * "blank" lines..
 * 
 * Typically used with a FileReader or InputStreamReader
 * 
 * @author Frank Carver
 */
public class SkipBlankLineIterator extends ReaderLineIterator {
    
    public SkipBlankLineIterator(Reader in, boolean autoclose) {
        super(in, autoclose);
    }

    public SkipBlankLineIterator(Reader in) {
        super(in);
    }


    protected String readLine() throws IOException {
        String ret = super.readLine();
        while (ret != null && isBlank(ret)) {
            ret = super.readLine();
        }
        return ret;
    }
    
    protected boolean isBlank(String line) {
        return StringUtils.isBlank(line);
    }
}
