package org.stringtree.juicer.string;


public class JoinStringFilter extends PassStringFilter {
    
	private StringBuffer buf;

	public JoinStringFilter(StringSource source) {
		super(source);
	}

	public JoinStringFilter() {
		// this method intentionally left blank
	}

	protected void collect(String s) {
		buf.append(s);
	}

	public String nextString() {
		buf = new StringBuffer();
		for (String s = source.nextString(); s != null; s = source.nextString()) {
			collect(s);
		}
		return buf.toString();
	}
}
