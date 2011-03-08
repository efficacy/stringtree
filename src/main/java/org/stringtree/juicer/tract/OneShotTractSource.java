package org.stringtree.juicer.tract;

import org.stringtree.Tract;
import org.stringtree.juicer.OneShotSource;

public abstract class OneShotTractSource extends OneShotSource implements TractSource {
    
	public Tract nextTract() {
		return (Tract)next();
	}
}
