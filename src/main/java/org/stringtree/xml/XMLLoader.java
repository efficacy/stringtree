package org.stringtree.xml;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.stringtree.Repository;
import org.stringtree.fetcher.hierarchy.HierarchyHelper;
import org.stringtree.fetcher.hierarchy.HierarchyLoader;

public class XMLLoader extends RootTagHandler implements HierarchyLoader {
	private String key;
    private HierarchyHelper helper;
    
    public XMLLoader(HierarchyHelper helper) {
        this.helper = helper;
    }
	
	public Object doPair(Object context, String name, Map<String, String> args, Reader in) throws IOException {
        Repository storer = (Repository)context;
		String oldCdata = getCdata();
        String oldKey = key;
        
		key = helper.createChildKey(storer, key, name);

		Object ret = super.doPair(context, name, args, in);
        helper.putCdata(storer, key, getCdata());
        helper.setAttributes(storer, key, args);
		
		key = oldKey;
		setCdata(oldCdata);
		
		return ret;
	}
	
	public Object doSingle(Object context, String name, Map<String, String> args) {
        Repository storer = (Repository)context;
        Object ret = super.doSingle(context, name, args);
        String child = helper.createChildKey(storer, key, name);
        
        helper.putCdata(storer, child, "");
        helper.setAttributes(storer, child, args);
		
		return ret;
	}

    public void load(Repository repository, Reader reader) throws IOException {
        key = null;
    	run(repository, reader);
	}
}

