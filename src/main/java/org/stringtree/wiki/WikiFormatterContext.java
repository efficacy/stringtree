package org.stringtree.wiki;

import org.stringtree.Container;
import org.stringtree.Fetcher;
import org.stringtree.fetcher.MapFetcher;

public class WikiFormatterContext extends MapFetcher {
    
	public WikiFormatterContext(Container pages, Fetcher remoteNames) {
		put("wiki.pages", pages);
		put("wiki.remoteNames", remoteNames);
	}
}
