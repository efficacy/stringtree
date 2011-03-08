package org.stringtree.util;

public class SequentialIdSource implements IdSource {

    private long initial;
	protected long i;

	public SequentialIdSource(long i) {
		this.i = i;
		this.initial = i;
	}

	public SequentialIdSource(String s) {
		this(LongNumberUtils.longValue(s));
	}

	public SequentialIdSource() {
		this(1);
	}

	public synchronized String next() {
		return Long.toString(i++);
	}

    public boolean valid(String id) {
        return id.matches("[0-9]+");
    }
    
    public synchronized void reset() {
        i = initial;
    }
}
