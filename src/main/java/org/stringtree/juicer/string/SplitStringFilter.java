package org.stringtree.juicer.string;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class SplitStringFilter extends PassStringFilter {
    
	protected CharacterIterator ci = null;
	protected char c = CharacterIterator.DONE;
	protected char separator = ' ';
	protected StringBuffer buf = new StringBuffer();

	public SplitStringFilter(StringSource source) {
		super(source);
	}

	public SplitStringFilter(char separator, StringSource source) {
		super(source);
		setSeparator(separator);
	}

	public SplitStringFilter(char separator) {
		setSeparator(separator);
	}

	public SplitStringFilter() {
		// this method intentionally left blank
	}
	
	public void connectSource(StringSource source) {
		super.connectSource(source);
		startNextInput();
	}

	public void setSeparator(char separator) {
		this.separator = separator;
	}

	private void startNextInput() {
		String input = nextInput();
		if (input != null) {
			ci = new StringCharacterIterator(input);
			c = ci.first();
		} else {
			ci = null;
			c = CharacterIterator.DONE;
		}
	}
	
	protected String nextInput() {
		return source.nextString();
	}

	protected boolean accept(char c) {
		return c != separator;
	}

	protected boolean isSeparator(char c) {
		return c == separator;
	}

	protected void put(char c) {
		buf.append(c);
	}

	public String nextString() {
		buf.setLength(0);

     	while(c != CharacterIterator.DONE) {
			if (accept(c)) {
				put(c);
			}

			c = ci.next();

			if (isSeparator(c)) {
				c = ci.next();
				break;
			}
		}

		if (c == CharacterIterator.DONE) {
			startNextInput();
		}

		if (buf.length() == 0 && ci == null) {
			return null;
		}

		return buf.toString();
	}
}
