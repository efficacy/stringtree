package org.stringtree.timing;

public class SystemClock implements Clock {
	public static SystemClock it = new SystemClock();
	
	public long getTime() {
		return System.currentTimeMillis();
	}
}
