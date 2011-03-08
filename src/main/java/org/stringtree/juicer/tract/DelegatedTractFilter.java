package org.stringtree.juicer.tract;

import org.stringtree.Tract;
import org.stringtree.util.Delegator;

public class DelegatedTractFilter extends Delegator implements TractFilter {
    
	public DelegatedTractFilter(TractFilter other) {
		super(other);
	}
	
	public TractFilter real() {
		return (TractFilter)getOther();
	}

	public Tract filter(Tract tract) {
		return real().filter(tract);
	}

	public Tract nextTract() {
		return real().nextTract();
	}

	public void connectSource(TractSource source) {
		real().connectSource(source);
	}
}
