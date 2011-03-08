package org.stringtree.juicer.string;

public class PassStringFilter extends AbstractStringDestination implements StringFilter {
    
	public static final PassStringFilter it = new PassStringFilter();

	public PassStringFilter(StringSource source) {
		super(source);
	}

	public PassStringFilter() {
		// this method intentionally left blank
	}
	
	public String nextString() {
		String ret = source.nextString();
		if (ret != null) {
			ret = filter(ret);
		}

		return ret;
	}

	public String filter(String input) {
		return input;
	}
}
