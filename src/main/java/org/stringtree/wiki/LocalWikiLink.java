package org.stringtree.wiki;

import org.stringtree.Container;
import org.stringtree.Fetcher;
import org.stringtree.juicer.Initialisable;

public class LocalWikiLink  implements Initialisable, Fetcher {
    
	private Container pages;

	public LocalWikiLink(Container pages) {
		this.pages = pages;
	}

	public LocalWikiLink() {
		this(null);
	}
	
	public Object getObject(String key) {
		String ret = "";
		
		if (pages.contains(key)) {
			ret = "view{" + key + "}";
		} else {
			ret = "edit{" + key + "}";
		}
		return ret;
	}

	public void init(Fetcher context) {
		pages = (Container)context.getObject("wiki.pages");
	}
}
