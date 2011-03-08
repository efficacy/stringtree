package org.stringtree.juicer.string;

import org.stringtree.juicer.Rewindable;

public class ArrayStringSource implements StringSource, Rewindable {
    
	private String[] array;
	private int index;

	public ArrayStringSource(String[] array) {
		this.array = array;
		index = 0;
	}

	public String nextString() {
		String ret = null;
		
		if (array != null && index < array.length) { 
			ret = array[index++];
		}

		return ret;
	}

	public void rewind() {
		index = 0;
	}
}
