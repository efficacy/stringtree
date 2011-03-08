package org.stringtree.concurrent;

public abstract class SimpleThreadFunnel<T,U> extends ThreadFunnel<T> {
	
	protected U peer;
	
	public SimpleThreadFunnel(U peer) {
		this.peer = peer;
	}
}
