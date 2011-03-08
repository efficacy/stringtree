package org.stringtree.juicer.formatter;

import org.stringtree.Fetcher;
import org.stringtree.juicer.string.StringSource;
import org.stringtree.juicer.tract.ExternalTractPipeline;
import org.stringtree.juicer.tract.TractFilterFetcher;

public class ExternalFormatter extends TractFilterFetcher {
    
	public ExternalFormatter(StringSource spec, Fetcher context) {
		setFilter(new ExternalTractPipeline(spec, context));
	}
	
	public ExternalFormatter(String spec, Fetcher context) {
		setFilter(new ExternalTractPipeline(spec, context));
	}
}
