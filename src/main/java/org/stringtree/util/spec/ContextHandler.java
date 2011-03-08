package org.stringtree.util.spec;

import org.stringtree.finder.StringFinder;

public abstract class ContextHandler extends SimpleHandler {
    
    protected StringFinder context;

    public void open(StringFinder context) {
        this.context = context;
        super.open(context);
    }
}
