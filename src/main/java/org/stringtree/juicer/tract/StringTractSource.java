package org.stringtree.juicer.tract;

import org.stringtree.tract.MapTract;

public class StringTractSource extends OneShotTractSource {
    
	private String text;

	public StringTractSource(String text) {
		this.text = text;
	}

	protected Object get() {
		return new MapTract(text);
	}
}
