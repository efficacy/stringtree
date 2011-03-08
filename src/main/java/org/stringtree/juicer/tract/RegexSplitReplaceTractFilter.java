package org.stringtree.juicer.tract;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.juicer.*;
import org.stringtree.juicer.Initialisable;
import org.stringtree.juicer.regex.MatcherStringFetcher;
import org.stringtree.juicer.string.FactoryRegexReplaceStringFilter;
import org.stringtree.juicer.string.StringFilter;
import org.stringtree.juicer.string.StringStringSource;

public class RegexSplitReplaceTractFilter extends RegexTokenFinderTractFilter
	implements Initialisable, AugmentedTransformation {
    
	protected String to;
	private String prefix;
	private String destpattern;
	private Fetcher augment;

	public RegexSplitReplaceTractFilter(String from, String to, boolean lock, String prefix, Fetcher augment) {
		super(from, lock);
		this.to = to;
		this.prefix = prefix;
		setAugment(augment);
		destpattern = prefix + "([~0123456789]+|" + prefix + ")";
	}

	public RegexSplitReplaceTractFilter(String from, String to, String prefix, Fetcher augment) {
		this(from, to, true, prefix, augment);
	}

	public RegexSplitReplaceTractFilter(String from, String to, boolean lock, Fetcher augment) {
		this(from, to, lock, "&", augment);
	}

	public RegexSplitReplaceTractFilter(String from, String to, Fetcher augment) {
		this(from, to, true, "&", augment);
	}

	public RegexSplitReplaceTractFilter(String from, String to, boolean lock) {
		this(from, to, lock, null);
	}

	public RegexSplitReplaceTractFilter(String from, String to) {
		this(from, to, null);
	}
	
	public void setAugment(Fetcher augment) {
		this.augment = augment;
	}

	protected void processToken(Tract ret) {
		MatcherStringFetcher factory = new MatcherStringFetcher(matcher, prefix, augment);
		StringFilter dest = new FactoryRegexReplaceStringFilter(destpattern, factory, 1);
		dest.connectSource(new StringStringSource(to));
		
		ret.setContent(dest.nextString());
		index = matcher.end();
		find();
	}

	public void init(Fetcher context) {
		if (augment instanceof Initialisable) {
			((Initialisable)augment).init(context);
		}
	}
}
