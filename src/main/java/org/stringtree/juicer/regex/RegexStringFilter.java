package org.stringtree.juicer.regex;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import org.stringtree.regex.Pattern;

import org.stringtree.juicer.string.PassStringFilter;
import org.stringtree.juicer.string.StringSource;

public abstract class RegexStringFilter extends PassStringFilter {
    
	protected Pattern compiled;

	public RegexStringFilter(String from, StringSource source) {
		super(source);
		this.compiled = Pattern.compile(from);
	}

	public RegexStringFilter(String from) {
		this.compiled = Pattern.compile(from);
	}

	protected String escapeBackslashes(String input) {
		StringBuffer buf = new StringBuffer();
		CharacterIterator ci = new StringCharacterIterator(input);
		for(char c = ci.first(); c != CharacterIterator.DONE; c = ci.next()) {
			if (c=='\\') {
				buf.append('\\');
			}
			buf.append(c);
		}
		return buf.toString();
	}
}
