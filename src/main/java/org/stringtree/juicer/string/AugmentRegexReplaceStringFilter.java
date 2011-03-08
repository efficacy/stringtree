package org.stringtree.juicer.string;

import org.stringtree.regex.Matcher;

import org.stringtree.Fetcher;
import org.stringtree.juicer.AugmentedTransformation;
import org.stringtree.juicer.Initialisable;
import org.stringtree.juicer.regex.MatcherStringFetcher;

public class AugmentRegexReplaceStringFilter extends DynamicRegexReplaceStringFilter 
    implements AugmentedTransformation, Initialisable {
    
	protected String to;
	protected Fetcher augment;
	protected String prefix;
	protected String destpattern; 

	public AugmentRegexReplaceStringFilter(String from, String to, Fetcher augment) {
		super(from);
		this.to = to;
		setAugment(augment);
		prefix = "&";
		destpattern = prefix + "([~0123456789]+|" + prefix + ")";
	}

	public AugmentRegexReplaceStringFilter(String from, String to) {
		this(from, to, null);
	}

	public String filter(Matcher matcher) {
		MatcherStringFetcher factory = new MatcherStringFetcher(matcher, prefix, augment);
		StringFilter dest = new FactoryRegexReplaceStringFilter(destpattern, factory, 1);
		dest.connectSource(new StringStringSource(to));
		
		return dest.nextString();
	}

	public void setAugment(Fetcher augment) {
		this.augment = augment;
	}

	public void init(Fetcher context) {
		if (augment instanceof Initialisable) {
			((Initialisable)augment).init(context);
		}
	}
}
