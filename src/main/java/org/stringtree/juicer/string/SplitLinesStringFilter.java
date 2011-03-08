package org.stringtree.juicer.string;

public class SplitLinesStringFilter extends SplitStringFilter {
    
	public SplitLinesStringFilter() {
		super('\n');
	}

	public SplitLinesStringFilter(StringSource source) {
		this();
		connectSource(source);
	}

	protected boolean accept(char c) {
		return super.accept(c) && c != '\r';
	}
}
