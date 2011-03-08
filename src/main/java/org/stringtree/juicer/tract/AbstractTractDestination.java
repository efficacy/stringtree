package org.stringtree.juicer.tract;

public abstract class AbstractTractDestination implements TractDestination {
    
	protected TractSource source;

	public void connectSource(TractSource source) {
		this.source = source;
	}
}
