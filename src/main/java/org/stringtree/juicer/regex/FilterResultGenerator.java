package org.stringtree.juicer.regex;

import org.stringtree.juicer.string.DynamicRegexReplaceStringFilter;
import org.stringtree.regex.Matcher;
import org.stringtree.regex.ResultGenerator;

public class FilterResultGenerator implements ResultGenerator {
    
	private DynamicRegexReplaceStringFilter filter;
	
	public FilterResultGenerator(DynamicRegexReplaceStringFilter filter) {
		this.filter = filter;
	}
	
	public String result(Matcher matcher) {
		return filter.filter(matcher);
	}
}
