package org.stringtree.juicer.string;

import org.stringtree.juicer.OneShotSource;

public abstract class OneShotStringSource extends OneShotSource implements StringSource {
    
	public String nextString() {
		return (String)next();
	}
}
