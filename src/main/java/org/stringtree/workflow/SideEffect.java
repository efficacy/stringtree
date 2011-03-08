package org.stringtree.workflow;

public interface SideEffect {
	/**
	 initialize any shared parameters for all instances of this side-effect
	*/
	void init(Object params);
	
	/**
	 execute a side effect
	 @return true if the transition should continue, false otherwise
	*/
	boolean execute(String from, String code, String to, Object context);
}