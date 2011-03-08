package org.stringtree.pool;

public interface ObjectFactory<T extends Verifiable> {
	void setPool(Pool<T> pool);
	T create();
	void dispose(T t);
}
