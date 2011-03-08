package org.stringtree.pool;

import org.stringtree.timing.Ticklish;

public interface Pool<T extends Verifiable> extends Ticklish {
	T claim();
	void release(T t);
}
