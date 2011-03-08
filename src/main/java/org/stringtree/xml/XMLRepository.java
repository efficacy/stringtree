package org.stringtree.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.stringtree.Repository;
import org.stringtree.fetcher.DelegatedRepository;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.fetcher.hierarchy.FlatHierarchyHelper;
import org.stringtree.fetcher.hierarchy.HierarchyHelper;
import org.stringtree.util.Utils;

public class XMLRepository extends DelegatedRepository {
    private XMLLoader loader;
    private XMLStorer storer;
    
    public XMLRepository(Repository repository, HierarchyHelper helper) {
        super(repository);
        loader = new XMLLoader(helper);
        storer = new XMLStorer(helper);
    }
    
    public XMLRepository(Repository repository) {
        this(repository, new FlatHierarchyHelper());
    }
    
    public XMLRepository() {
        super(new MapFetcher());
    }
    
    public void load(Reader reader) throws IOException {
        loader.load(realRepository(), reader);
    }
    
    public void store(Writer writer) throws IOException {
        storer.store(realRepository(), writer);
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof XMLRepository)) return false;
        
        XMLRepository other = (XMLRepository)obj;
        return super.equals(obj) && Utils.same(this.loader, other.loader)
            && Utils.same(storer, other.storer);
    }
    
    @Override
    public int hashCode() {
        return "$XMLRepository$".hashCode() + super.hashCode(); 
    }
}
