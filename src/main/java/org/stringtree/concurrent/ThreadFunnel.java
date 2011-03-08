package org.stringtree.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class ThreadFunnel<T> extends Thread {
	private ConcurrentLinkedQueue<T> queue;
	private volatile boolean running;
	
	public ThreadFunnel() {
		this.queue = new ConcurrentLinkedQueue<T>();
		this.running = false;
	}

	public synchronized void enqueue(T event) {
		queue.offer(event);
//System.err.println("about to notify (" + queue.size() + ")");
		notify();
	}
	
	@Override
	public synchronized void run() {
		running = true;
		for (T event = queue.poll(); running; event = queue.poll()) {
//System.err.println("polled (" + queue.size() + ") and got " + event);
			if (null == event) {
				try {
//System.err.println("waiting...");
					wait();
//System.err.println("woke!");
				} catch (InterruptedException e) {
					running = false;
				}
			} else {
				forward(event);
			}
		}
	}
	
	protected abstract void forward(T event);

	public void halt() {
		interrupt();
		running = false;
	}

	public boolean isRunning() {
		return running;
	}
}
