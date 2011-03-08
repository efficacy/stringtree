package org.stringtree.juicer.formatter;

import org.stringtree.Fetcher;
import org.stringtree.juicer.Initialisable;
import org.stringtree.juicer.tract.DelegatedTractFilter;

public abstract class Transformation extends DelegatedTractFilter implements Initialisable {
    
	protected String name;

	public Transformation() {
		super(null);
	}
	
	public abstract void init(String tail);

	protected void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public void init(Fetcher context) {
		if (other instanceof Initialisable) {
			((Initialisable)other).init(context);
		}
	}
}
