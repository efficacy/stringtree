package org.stringtree.workflow;

public abstract class BasicSideEffect implements SideEffect {
    
	protected Object params;
	protected boolean verbose;
	
	public void init(Object params) {
		this.params = params;
		verbose = false;
	}
	
	public abstract boolean execute(String from, String code, String to, Object context);
	
	public void term() 	{
    	// this method intentionally left blank
	}
}