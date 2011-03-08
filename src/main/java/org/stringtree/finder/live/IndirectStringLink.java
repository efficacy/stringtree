package org.stringtree.finder.live;

public class IndirectStringLink extends ObjectLink {
    
    public IndirectStringLink(String name) {
        super(name);
    }

    public Object getObject() {
        Object ret = null;
        if (context != null && name != null) {
            ret = context.getObject(context.get(name));
        }
        
        return ret;
    }
}
