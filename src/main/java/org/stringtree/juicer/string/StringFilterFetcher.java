package org.stringtree.juicer.string;

import org.stringtree.Fetcher;

public class StringFilterFetcher implements Fetcher {
    
	protected StringFilter filter;
	
	public StringFilterFetcher(StringFilter filter) {
		this.filter = filter;
	}
	
	public Object getObject(String key) {
		filter.connectSource(new StringStringSource(key));
		return filter.nextString();
	}
}
