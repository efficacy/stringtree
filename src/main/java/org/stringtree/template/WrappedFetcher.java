package org.stringtree.template;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.DelegatedFetcher;
import org.stringtree.util.Utils;

public class WrappedFetcher extends DelegatedFetcher {
    
    private Object wrapped;

    public WrappedFetcher(Object obj, Fetcher realFetcher) {
        super(realFetcher);
        this.wrapped = obj;
    }

    public Object getObject(String name) {
        return "this".equals(name) ? wrapped : super.getObject(name);
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof WrappedFetcher)) return false;
        
        WrappedFetcher other = (WrappedFetcher)obj;
        return super.equals(obj) && Utils.same(this.wrapped, other.wrapped);
    }
    
    @Override
    public int hashCode() {
        return "$WrappedFetcher$".hashCode() + super.hashCode(); 
    }
}
