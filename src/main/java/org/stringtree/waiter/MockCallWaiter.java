package org.stringtree.waiter;

import org.stringtree.mock.RecordingMock;

class CallCountEvaluator implements Evaluator {
	private RecordingMock mock;
	private int nCalls;

	public CallCountEvaluator(RecordingMock mock, int nCalls) {
		this.mock = mock;
		this.nCalls = nCalls;
	}
	
	public boolean done() {
		return mock.recorded.size() >= nCalls;
	}
}

public class MockCallWaiter extends Waiter {
	private RecordingMock mock;
	
	public MockCallWaiter(RecordingMock mock) {
		this.mock = mock;
	}
	
	public boolean waitOrTimeout(int nCalls, long timeout) {
		return super.waitOrTimeout(new CallCountEvaluator(mock, nCalls), timeout);
	}
}
