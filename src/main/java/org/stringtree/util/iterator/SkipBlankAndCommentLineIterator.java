package org.stringtree.util.iterator;

import java.io.Reader;

/**
 * An Iterator to iterate through the "lines" of a stream or reader, skipping
 * "blank" lines and comments..
 * 
 * Comments are identified as any line whch starts with a supplied prefix
 * (typically "#" or "rem" etc.)
 * 
 * Typically used with a FileReader or InputStreamReader.
 * 
 * @author Frank Carver
 */
public class SkipBlankAndCommentLineIterator extends SkipBlankLineIterator {
    
    private String prefix;

    public SkipBlankAndCommentLineIterator(Reader in, boolean autoclose,
            String prefix) {
        super(in, autoclose);
        this.prefix = prefix;
    }

    public SkipBlankAndCommentLineIterator(Reader in, String prefix) {
        super(in);
        this.prefix = prefix;
    }

    protected boolean isBlank(String line) {
        boolean ret = (prefix != null && line.startsWith(prefix))
                || line.trim().length() == 0;
        return ret;
    }
}
