package org.stringtree.juicer.tract;

import org.stringtree.Tract;
import org.stringtree.juicer.JuicerConvertHelper;

public class JoinTractFilter extends BasicTractFilter {
    
	private StringBuffer buf;

	protected void collect(String s) {
		buf.append(s);
	}

	public Tract nextTract() {
		buf = new StringBuffer();
		for (Tract t = source.nextTract(); t != null; t = source.nextTract()) {
			String s = t.getContent();
			collect(s);
		}

		return JuicerConvertHelper.expand((buf.length() > 0) ? buf.toString() : null);
	}
}
