package org.stringtree.juicer.string;

import org.stringtree.regex.Matcher;

import org.stringtree.Fetcher;

public class FactoryRegexReplaceStringFilter extends DynamicRegexReplaceStringFilter {
    
	protected Fetcher to;
	protected int group;

	public FactoryRegexReplaceStringFilter(String from, Fetcher to, int group) {
		super(from);
		this.to = to;
		this.group = group;
	}

	public FactoryRegexReplaceStringFilter(String from, Fetcher to) {
		this(from, to, 0);
	}

	public String filter(Matcher matcher) {
		return (String)to.getObject(matcher.group(group));
	}
}
