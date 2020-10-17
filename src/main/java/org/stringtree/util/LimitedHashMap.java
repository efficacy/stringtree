package org.stringtree.util;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class LimitedHashMap<T1, T2> extends HashMap<T1, T2> {
	protected Queue<T1> queue;
	protected int limit;

	public LimitedHashMap(int limit) {
		this.limit = limit;
		this.queue = new ArrayDeque<T1>(limit);
	}

	@Override
	public synchronized T2 put(T1 key, T2 value) {
		if (size() >= limit) evict();
		queue.add(key);
		return super.put(key, value);
	}

	private void evict() {
		remove(queue.remove());
	}
}
