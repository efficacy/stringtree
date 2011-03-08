package org.stringtree.juicer.string;

import org.stringtree.regex.Matcher;
import org.stringtree.regex.ResultGenerator;

import org.stringtree.juicer.regex.FilterResultGenerator;
import org.stringtree.juicer.regex.RegexStringFilter;

public abstract class DynamicRegexReplaceStringFilter extends RegexStringFilter {
	public DynamicRegexReplaceStringFilter(String from) {
		super(from);
	}

	public String filter(String text) {
        ResultGenerator gen = new FilterResultGenerator(this);
		return compiled.replaceAll(text, gen);
	}

	public abstract String filter(Matcher matcher);
}
