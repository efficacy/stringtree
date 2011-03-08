package org.stringtree.regex;

public class Matcher {
    
	protected java.util.regex.Matcher real;

	public Matcher(java.util.regex.Matcher real) {
		this.real = real;
	}
	
    public String group() {
        return real.group();
    }

	public String group(int group) {
        return real.group(group);
    }

	public void dumpGroups() {
		int ng = groupCount();
		for (int i = 0; i < ng; ++i) {
			System.out.println(" group " + i + "='" + real.group(i) + "'");
		}
	}
	
	public boolean find() {
		return real.find();
	}
	
	public int start() {
		return real.start();
	}
	
	public int end() {
		return real.end();
	}
	
	public int groupCount() {
		return real.groupCount();
	}
}