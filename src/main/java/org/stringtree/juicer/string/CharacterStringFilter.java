package org.stringtree.juicer.string;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public abstract class CharacterStringFilter extends PassStringFilter {
    
	public CharacterStringFilter(StringSource source) {
		super(source);
	}

	public CharacterStringFilter() {
		// this method intentionally left blank
	}

	public String filter(String input) {
		StringBuffer buf = new StringBuffer();

		CharacterIterator ci = new StringCharacterIterator(input);
     	for(char c = ci.first(); c != CharacterIterator.DONE; c = ci.next()) {
			if (accept(c)) {
				put(c, buf);
			}
		}

		return buf.toString();
	}

	protected boolean accept(char c) {
		return true;
	}

	protected void put(char c, StringBuffer buf) {
		buf.append(c);
	}
}
