package org.stringtree.juicer.tract;

import org.stringtree.juicer.string.AugmentRegexReplaceStringFilter;

public class RegexAugmentReplaceTractFilter extends AugmentedStringFilterTractFilter {
    
	public RegexAugmentReplaceTractFilter(String from, String to) {
		super(new AugmentRegexReplaceStringFilter(from, to));
	}

	public RegexAugmentReplaceTractFilter(String from, String to, boolean polite) {
		super(new AugmentRegexReplaceStringFilter(from, to), polite);
	}
}
