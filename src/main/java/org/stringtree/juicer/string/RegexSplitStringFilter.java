package org.stringtree.juicer.string;

import org.stringtree.juicer.regex.RegexStringFilter;

public class RegexSplitStringFilter extends RegexStringFilter {
    
	protected String[] split;
	protected int index;

	public RegexSplitStringFilter(String pattern, StringSource source) {
		super(pattern, source);
	}

	public RegexSplitStringFilter(String pattern) {
		super(pattern);
	}

	private void startNextInput() {
		String input = source.nextString();
		if (input == null) {
			split = null;
		} else {
			split = compiled.split(input);
		}

		index = 1;
	}

	public void connectSource(StringSource source) {
		super.connectSource(source);
		startNextInput();
	}
	
	public String nextString() {
		if (split == null) return null;

		String ret = split[index++];
		if (index >= split.length) {
			startNextInput();
		}

		return ret;
	}
}
