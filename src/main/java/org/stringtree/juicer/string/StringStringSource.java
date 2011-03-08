package org.stringtree.juicer.string;

public class StringStringSource extends OneShotStringSource {
    
	private String text;

	public StringStringSource(String text) {
		this.text = text;
	}

	protected Object get() {
		return text;
	}
}
