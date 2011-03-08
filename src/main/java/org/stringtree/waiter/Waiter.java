package org.stringtree.waiter;

public class Waiter {
	private long delay;
	
	public Waiter(long delay) {
		this.delay = delay;
	}
	
	public Waiter() {
		this(100);
	}
	
	public boolean waitOrTimeout(Evaluator evaluator, long timeout) {
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() < start + timeout) {
			if (evaluator.done()) return true;
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				return false;
			}
		}
		
		return false;
	}
}
