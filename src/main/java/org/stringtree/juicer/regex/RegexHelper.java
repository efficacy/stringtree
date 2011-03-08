package org.stringtree.juicer.regex;

import org.stringtree.regex.Matcher;

public class RegexHelper {
    
	public static void dumpGroups(Matcher matcher) {
		int ng = matcher.groupCount();
		for (int i = 0; i < ng; ++i) {
			System.out.println(" group " + i + "='" + matcher.group(i) + "'");
		}
	}
}