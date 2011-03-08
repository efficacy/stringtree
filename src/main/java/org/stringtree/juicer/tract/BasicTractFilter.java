package org.stringtree.juicer.tract;

import org.stringtree.Tract;

public abstract class BasicTractFilter extends AbstractTractDestination implements TractFilter {
    
	public Tract nextTract() {
		Tract ret = source.nextTract();
		if (ret != null) {
			ret = filter(ret);
		}

		return ret;
	}

	public Tract filter(Tract input) {
		return input;
	}
}
