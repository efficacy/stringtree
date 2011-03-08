package org.stringtree.xmlevents;

import java.util.Stack;

public class HierarchyHistory<T> implements History<T> {
	
	private Stack<T> history;
	private boolean lax;
	
	public HierarchyHistory(Stack<T> history, boolean lax) {
		this.lax = lax;
		this.history = history;
	}
	
	@SuppressWarnings("unchecked")
    public HierarchyHistory(boolean lax) {
		this((Stack<T>)new Stack<Object>(), lax);
	}

	public T current() {
		return history.isEmpty() ? null : history.peek();
	}

	public int depth() {
		return history.size();
	}

	public void forward(T state) {
		history.push(state);
	}

	public T back(T state) {
	    if (history.isEmpty())  return null;

	    if (!lax && !history.peek().equals(state)) throw new IllegalStateException("got back('" + state + "') expected back('" + history.peek() + "')");
		
		return history.pop();
	}

	@SuppressWarnings("unchecked")
    public Stack<T> history() {
		return (Stack<T>)history.clone();
	}

	public T previous() {
		int n = history.size();
		return n < 2 ? null : history.get(n-2);
	}

    @SuppressWarnings("unchecked")
    public Object clone() {
	    HierarchyHistory ret = null;
	    Stack history = (Stack)this.history.clone();
	    try {
            ret = (HierarchyHistory)super.clone();
            ret.history = history;
        } catch (CloneNotSupportedException e) {
            ret = new HierarchyHistory(history, lax);
        }
        
        return ret;
	}

	public String toPath() {
		StringBuffer ret = new StringBuffer();
		for (T t : history) {
			ret.append("/");
			ret.append(t);
		}
		return ret.toString();
	}
	
	public String toString() {
	    return "History(" + history.size() + ")" + history;
	}
}
