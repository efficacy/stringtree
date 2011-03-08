package org.stringtree.timing;

public class FixableClock implements Clock {
	private long start;
	private long time;
	
	public FixableClock(long time) {
		setTime(time);
	}
	
	public FixableClock() {
		this(0);
	}

	@Override
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
		start = time;
	}

	public long advanceBy(long millis) {
		time += millis;
		return time;
	}

	public long advanceTo(long millis) {
		time = start + millis;
		return time;
	}
}
