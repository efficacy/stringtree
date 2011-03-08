package org.stringtree.waiter;

public abstract class SelfWaiter extends Waiter implements Evaluator {
	public SelfWaiter(long delay) {
		super(delay);
	}

	public SelfWaiter() {
	}

	public boolean waitOrTimeout(long timeout) {
		return waitOrTimeout(this, timeout);
	}
}
