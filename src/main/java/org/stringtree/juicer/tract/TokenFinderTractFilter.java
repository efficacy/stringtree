package org.stringtree.juicer.tract;

public class TokenFinderTractFilter extends RegexTokenFinderTractFilter {
    
	public TokenFinderTractFilter() {
		super("@(.*)@",1);
	}
}
