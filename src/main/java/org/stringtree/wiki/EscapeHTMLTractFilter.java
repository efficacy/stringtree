package org.stringtree.wiki;

import org.stringtree.juicer.string.StaticRegexReplaceStringFilter;
import org.stringtree.juicer.string.StringFilter;
import org.stringtree.juicer.string.StringPipeline;
import org.stringtree.juicer.tract.StringFilterTractFilter;

public class EscapeHTMLTractFilter extends StringFilterTractFilter {
    
	public EscapeHTMLTractFilter() {
		super(new StringPipeline(new StringFilter[] {
            // TODO what about the rest of these ?
			new StaticRegexReplaceStringFilter("<","&lt;"),
			new StaticRegexReplaceStringFilter(">","&gt;"),
		}));
	}
}
