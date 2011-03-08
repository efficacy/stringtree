package org.stringtree.wiki;

import org.stringtree.Fetcher;
import org.stringtree.juicer.Initialisable;

public class RemoteWikiLink implements Initialisable, Fetcher {
    
	private Fetcher remoteNames;
	
	public RemoteWikiLink(Fetcher remoteNames) {
		this.remoteNames = remoteNames;
	}
	
	public RemoteWikiLink() {
		this(null);
	}
	
	public Object getObject(String key) {
		Object ret = (remoteNames == null) 
			? null 
			: remoteNames.getObject(key);

		if (ret != null) {
			ret = "remote{" + ret + "}";
		}

		return ret;
	}

	public void init(Fetcher context) {
		if (context != null) {
			remoteNames = (Fetcher)context.getObject("wiki.remoteNames");
		}
	}
}
