package org.stringtree.juicer.tract;

import org.stringtree.juicer.string.StaticRegexReplaceStringFilter;

public class RegexReplaceTractFilter 
	extends StringFilterTractFilter
{
	public RegexReplaceTractFilter(String from, String to)
	{
		super(new StaticRegexReplaceStringFilter(from, to));
	}

	public RegexReplaceTractFilter(String from, String to, boolean polite)
	{
		super(new StaticRegexReplaceStringFilter(from, to), polite);
	}
}
