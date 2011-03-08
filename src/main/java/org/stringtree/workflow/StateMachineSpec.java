package org.stringtree.workflow;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class StateMachineSpec {
    
	private static final int BACK = 0;
	private static final int RESET = 1;
	private static final int SELF = 2;
	private static final String SEP = "?";

	private String[] metacodes;

	public StateMachineSpec() {
		metacodes = new String[3];
		metacodes[BACK] = "_BACK";
		metacodes[RESET] = "_RESET";
		metacodes[SELF] = "_SELF";
	}

	private Map<String, Object> map = new HashMap<String, Object>();
	private String initial = null;

	public void reset() {
		map.clear();
		initial = null;
	}

	public void setSymbol(int metacode, String symbol) {
		metacodes[metacode] = symbol;
	}

	public String getSymbol(int metacode) {
		return metacodes[metacode];
	}

	public void setBackSymbol(String symbol) {
		setSymbol(BACK, symbol);
	}

	public String getBackSymbol() {
		return getSymbol(BACK);
	}

	public void setResetSymbol(String symbol) {
		setSymbol(RESET, symbol);
	}

	public String getResetSymbol() {
		return getSymbol(RESET);
	}

	public void setSelfSymbol(String symbol) {
		setSymbol(SELF, symbol);
	}

	public String getSelfSymbol() {
		return getSymbol(SELF);
	}

	public Object getDestination(Object source, String exitcode) {
		return map.get(combine(source, exitcode));
	}

	public void cloneState(String oldstate, String newstate) {
		Map<String, Object> temp = new HashMap<String, Object>();
		String[] array = new String[2];

		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			split(key, array);
			if (oldstate.equals(array[0])) {
				temp.put(combine(newstate, array[1]), map.get(key));
			}
		}

		map.putAll(temp);
	}

	public Iterator<String> getExits(String state, boolean includeMetaCodes) {
		List<String> ret = new ArrayList<String>();
		String[] array = new String[2];

		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			split(key, array);
			if (state.equals(array[0])) {
				ret.add(array[1]);
			}
		}

		if (includeMetaCodes) {
			ret.add(getBackSymbol());
			ret.add(getResetSymbol());
		}

		return ret.iterator();
	}

	public void setInitialState(String state) {
		initial = state;
	}

	public String getInitialState() {
		return initial;
	}

	public void addDestination(String source, String code, Object dest) {
		map.put(combine(source, code), dest);
	}

	protected String combine(Object source, String exitcode) {
		return "" + source + SEP + exitcode;
	}

	protected void split(Object key, String[] ret) {
		String s = (String)key;
		int offset = s.indexOf(SEP);
		ret[0] = s.substring(0,offset);
		ret[1] = s.substring(offset+1);
	}
}
