package org.stringtree.finder.live;

import org.stringtree.finder.StringFinder;
import org.stringtree.util.StringUtils;

public class ObjectLink extends NamedLink {
    
    protected StringFinder context;

    public ObjectLink(String name) {
        super(name);
        this.context = null;
    }

    public void init(StringFinder context) {
        this.context = context;
    }

    public Object getObject() {
        return context != null ? context.getObject(name) : null;
    }

    public String toString() {
        return StringUtils.stringValue(getObject());
    }
}
