package org.stringtree.juicer.tract;

import org.stringtree.Tract;
import org.stringtree.juicer.Rewindable;

public class SequenceTractSource implements TractSource, Rewindable {
    
	private Tract[] sequence;
	private int index;
	
	public SequenceTractSource(Tract[] sequence) {
		this.sequence = sequence;
		rewind();
	}
	
	public Tract nextTract() {
		Tract ret = null;
		
		if (index < sequence.length) {
			ret = sequence[index++];
		}
		return ret;
	}

	public void rewind() {
		index = 0;
	}
}
