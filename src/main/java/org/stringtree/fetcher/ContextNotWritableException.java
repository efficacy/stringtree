package org.stringtree.fetcher;

import org.stringtree.Fetcher;

public class ContextNotWritableException extends UnsupportedOperationException {
    
    public ContextNotWritableException(Fetcher context) {
        super(context.toString());
    }
}
