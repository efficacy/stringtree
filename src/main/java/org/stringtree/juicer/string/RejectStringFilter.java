package org.stringtree.juicer.string;

public abstract class RejectStringFilter extends PassStringFilter {
    
	public RejectStringFilter(StringSource source) {
		super(source);
	}

	public RejectStringFilter() {
		// this method intentionally left blank
	}

	public String nextString() {
		String ret = source.nextString();
		while (ret != null && reject(ret)) {
			ret = source.nextString();
		}

		return filter(ret);
	}

	protected abstract boolean reject(String s);
}
