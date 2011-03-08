package org.stringtree.juicer.tract;

import org.stringtree.Tract;

public class EmptyTractSource implements TractSource {
    
	public static final TractSource it = new EmptyTractSource();

	public Tract nextTract() {
		return null;
	}
}
