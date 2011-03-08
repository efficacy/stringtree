package org.stringtree.juicer.string;


public abstract class AbstractStringDestination implements StringDestination {
    
	protected StringSource source;

	public AbstractStringDestination(StringSource source) {
		connectSource(source);
	}

	public AbstractStringDestination() {
		source = null;
	}

	public void connectSource(StringSource source) {
		this.source = source;
	}
}
