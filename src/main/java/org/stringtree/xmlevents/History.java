package org.stringtree.xmlevents;

import java.util.Stack;

public interface History<T> extends Cloneable {
	void forward(T state);
	T back(T state);
	Stack<T> history();
	T current();
	T previous();
	int depth();
	String toPath();
	
	Object clone();
}
