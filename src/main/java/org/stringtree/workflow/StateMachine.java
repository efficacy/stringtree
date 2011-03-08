package org.stringtree.workflow;

import java.util.Stack;
import java.util.Iterator;

public class StateMachine {
    
	private StateMachineSpec spec;
	private Stack<Object> history;
	private Object context;

	private static final String string(Object obj) 	{
		return obj != null ? obj.toString() : null;
	}

	public StateMachine(StateMachineSpec spec) {
		this.spec = spec;
		this.history = new Stack<Object>();
		history.push(spec.getInitialState());
	}

	public StateMachine(StateMachineSpec spec, Object context) {
		this(spec);
		this.context = context;
	}

	public void reset() {
		history.clear();
		history.push(spec.getInitialState());
	}

	public String getInitialState() {
		return spec.getInitialState().toString();
	}

	public void setContext(Object context) {
		this.context = context;
	}

	public Object getContext() {
		return context;
	}

	public String getState() {
		return history.isEmpty() ? null : string(history.peek());
	}

	private Object realLookBack() {
		int len = history.size();
		return len > 1 ? history.get(len-2) : null;
	}

	public String lookBack() {
		return string(realLookBack());
	}

	private Object realLookForward(String exitcode) {
		Object dest = spec.getDestination(getState(), exitcode);
		String state = string(dest);

		if (spec.getBackSymbol().equals(state) || state==null && spec.getBackSymbol().equals(exitcode)) {
			dest = realLookBack();
		} else if (spec.getResetSymbol().equals(state) || state==null && spec.getResetSymbol().equals(exitcode)) {
			dest = spec.getInitialState();
		} else if (spec.getSelfSymbol().equals(state) || state==null && spec.getSelfSymbol().equals(exitcode)) {
			dest = getState();
		}

		return dest;
	}

	public String lookForward(String exitcode) {
		return string(realLookForward(exitcode));
	}

	private boolean go(String from, String code, Object dest) {
		boolean ret = false;

		if (dest != null) {
			ret = true;

			if (dest instanceof Transition) {
				ret = ((Transition)dest).doSideEffect(from, code, context);
			}

			if (ret) {
				ret = false;

				int len = history.size();
				for (int i = 0; i < len; ++i) {
					if (dest.equals(history.get(i))) {
						history.setSize(i+1);
						ret = true;
						break;
					}
				}

				if (!ret) {
					history.push(dest);
					ret = true;
				}
			}
		}

		return ret;
	}

	public String next(String exitcode) {
		return go(getState(), exitcode, realLookForward(exitcode)) ? getState() : null;
	}

	public Iterator<String> getExits(boolean includeMetaCodes) {
		return spec.getExits(getState(), includeMetaCodes);
	}
}
