package org.stringtree.pool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ObjectPool<T extends Verifiable> implements Pool<T> {
	int min;
	ObjectFactory<T> factory;
	Queue<T> available;
	
	public ObjectPool(ObjectFactory<T> factory, int min) {
		this.min = min;
		this.factory = factory;
		factory.setPool(this);
		
		available = new ConcurrentLinkedQueue<T>();
		for (int i = 0; i < min; ++i) {
			available.add(factory.create());
		}
	}
	
	public ObjectPool(ObjectFactory<T> factory) {
		this(factory, 0);
	}
	
	@Override
	public synchronized T claim() {
		if (available.isEmpty()) {
			available.add(factory.create());
		}
		T ret = null;
		for(;;) {
			ret = available.poll();
//System.err.println("ObjectPool.claim head of available list is " + ret);
			if (null == ret || ret.valid()) break;
			
			if (!ret.valid()) {
				factory.dispose(ret);
			}
		}
//System.err.println("ObjectPool.claim about to return " + ret);
		if (null == ret) {
			ret = factory.create();
		}
//System.err.println("ObjectPool.claim " + ret);
		return ret;
	}

	@Override
	public synchronized void release(T t) {
//System.err.println("ObjectPool.close " + t);
		available.add(t);
	}

	@Override public void tickle(long time) {} // expiry entirely on pull in this one
}
