package org.stringtree.juicer.formatter;

import org.stringtree.juicer.string.SplitStringFilter;
import org.stringtree.juicer.string.StringStringSource;

public abstract class FromToTransformation extends Transformation {
    
	public void init(String tail) {
		tail = tail.trim();
		SplitStringFilter split = new SplitStringFilter();
		char sep = tail.charAt(0);
		
		split.connectSource(new StringStringSource(tail.substring(1)));
		split.setSeparator(sep);
		String from = split.nextString();
		String to = split.nextString();

		init(from, to);
	}
	
	public abstract void init(String from, String to);
}
