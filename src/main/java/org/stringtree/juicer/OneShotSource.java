package org.stringtree.juicer;

public abstract class OneShotSource implements Rewindable {
    
	protected boolean done = false;
	
	protected Object next() {
		Object ret = null;

		if (!done) {
			ret = get();
			done = true;
		}

		return ret;
	}
	
	protected abstract Object get();

	public void rewind() {
		done = false;
	}
}
