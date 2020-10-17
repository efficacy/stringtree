package org.stringtree.template;

import org.stringtree.Fetcher;
import org.stringtree.finder.StringFinder;

public class InlineTemplater extends RecursiveTemplater {

	private StringFinder context;

    public InlineTemplater(StringFinder context) {
    	this.context = context;
	}

	@Override protected Object getTemplate(String templateName, Fetcher context) {
        return templateName;
    }

	public String expand(String string) {
		StringCollector collector = new ByteArrayStringCollector();
		expand(context, string, collector);
		return collector.toString();
	}
}
