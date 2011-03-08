package org.stringtree.juicer.formatter;

import org.stringtree.Fetcher;
import org.stringtree.juicer.Initialisable;
import org.stringtree.juicer.tract.InitialisingTractPipeline;
import org.stringtree.juicer.tract.TractFilter;
import org.stringtree.juicer.tract.TractFilterFetcher;

public abstract class Formatter extends TractFilterFetcher implements Initialisable {
    
	public Formatter(Fetcher context) {
		init(context);
	}

	public Formatter() {
		// this method intentionally left blank
	}

	public void init(Fetcher context) {
		setFilter(new InitialisingTractPipeline(getFilters(context), context));
	}
	
	protected abstract TractFilter[] getFilters(Fetcher context);
}
