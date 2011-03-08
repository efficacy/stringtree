package org.stringtree.juicer.string;

import org.stringtree.juicer.regex.RegexStringFilter;

public class StaticRegexReplaceStringFilter extends RegexStringFilter {
    
	protected String to;

	public StaticRegexReplaceStringFilter(String from, String to) {
		super(from);
		this.to = to;
	}

	public String filter(String text) {
		String ret = compiled.replaceAll(text, to);
		return ret;
	}
}
