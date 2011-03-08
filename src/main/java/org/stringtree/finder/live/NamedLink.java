package org.stringtree.finder.live;

import org.stringtree.fetcher.LiveObjectWrapper;

public abstract class NamedLink implements LiveObjectWrapper {
    
    protected String name;

    public NamedLink(String name) {
        this.name = name;
    }

    public Object getRaw() {
        return name;
    }
}
