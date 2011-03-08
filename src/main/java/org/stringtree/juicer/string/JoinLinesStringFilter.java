package org.stringtree.juicer.string;

public class JoinLinesStringFilter extends JoinStringFilter {
    
	protected void collect(String s) {
		super.collect(s);
		super.collect("\n");
	}
}
