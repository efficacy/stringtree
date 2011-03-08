package org.stringtree.regex;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class Pattern {
    
	public static String processor="java.util.regex";
	protected java.util.regex.Pattern real;

	public Pattern(java.util.regex.Pattern real) {
		this.real = real;
	}

	public static Pattern compile(String text) {
		return new Pattern(java.util.regex.Pattern.compile(text, java.util.regex.Pattern.MULTILINE));
	}

	public String replaceAll(String input, ResultGenerator generator) {
        
		StringBuffer buf = new StringBuffer();
		Matcher matcher = matcher(input);

		while (matcher.real.find()) {
			matcher.real.appendReplacement(buf, escape(generator.result(matcher)));
		}
		matcher.real.appendTail(buf);

		return buf.toString();
	}

	public Matcher matcher(String input) {
		java.util.regex.Matcher realMatcher = real.matcher(input);
		return new Matcher(realMatcher);
	}

	public String replaceAll(String input, String value) {
		java.util.regex.Matcher matcher = real.matcher(input);
 		return matcher.replaceAll(value);
	}

	public String[] split(String input) {
		return real.split(input, -1);
	}

	private String escape(String input) {
        if (null == input) return "";
        
		StringBuffer buf = new StringBuffer();
		CharacterIterator ci = new StringCharacterIterator(input);
		for(char c = ci.first(); c != CharacterIterator.DONE; c = ci.next()) {
			if (c=='\\' || c=='$') {
				buf.append('\\');
			}
			buf.append(c);
		}
		return buf.toString();
	}
}