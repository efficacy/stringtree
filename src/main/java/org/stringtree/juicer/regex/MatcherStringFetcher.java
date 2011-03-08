package org.stringtree.juicer.regex;

import org.stringtree.Fetcher;
import org.stringtree.regex.Matcher;
import org.stringtree.regex.Pattern;

import org.stringtree.util.IntegerNumberUtils;
import org.stringtree.util.NumericValidator;

public class MatcherStringFetcher implements Fetcher
{
	private Matcher matcher;
	protected String prefix;
	private Fetcher augment;

	public MatcherStringFetcher(Matcher matcher, String prefix, Fetcher augment) {
		this.matcher = matcher;
		this.prefix = prefix;
		this.augment = augment;
	}

	public MatcherStringFetcher(Matcher matcher) {
		this(matcher,"&", null);
	}

	public MatcherStringFetcher(String pattern, String text) {
		this(Pattern.compile(pattern).matcher(text));
		matcher.find();
	}

	public String get(int group) {
		String ret = "";

		if (group <= matcher.groupCount()) {
			ret = matcher.group(group);
		}

		return ret;
	}
	
	public int count() {
		return matcher.groupCount();
	}

	public Object getObject(String key) {
		Object ret = null;
        
		if (prefix.equals(key)) {
			ret = prefix;
		} else if ("0".equals(key)) {
			ret = matcher.group();
		} else if (NumericValidator.isValidNumber(key)) {
			int group = IntegerNumberUtils.intValue(key);
			ret = get(group);
		} else if (key.startsWith("~") && augment != null) {
			ret = augment.getObject((String)getObject(key.substring(1)));
		}

		return ret;
	}
}
