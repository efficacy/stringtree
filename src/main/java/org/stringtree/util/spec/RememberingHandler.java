package org.stringtree.util.spec;

import org.stringtree.Storer;
import org.stringtree.finder.StringFinder;

public abstract class RememberingHandler implements Handler {

    protected Storer storer;
    protected StringFinder context;

    public RememberingHandler(Storer storer) {
        this.storer = storer;
    }

    public void close(StringFinder context) {
    }

    public void open(StringFinder context) {
        this.context = context;
    }
}
