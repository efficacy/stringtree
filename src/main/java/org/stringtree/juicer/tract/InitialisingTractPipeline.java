package org.stringtree.juicer.tract;

import java.util.Iterator;

import org.stringtree.Fetcher;
import org.stringtree.juicer.Initialisable;

public class InitialisingTractPipeline extends TractPipeline implements Initialisable {
    
	private Fetcher context;
	
	public InitialisingTractPipeline(TractFilter[] array, Fetcher context) {
		super(array);
		init(context);
	}

	public InitialisingTractPipeline(Fetcher context) {
		init(context);
	}

	protected InitialisingTractPipeline() {
		// this method intentionally left blank
	}
	
	public void init(Fetcher context) {
		this.context = context;
	}

	public void relink() {
		init();
		super.relink();
	}
	
	private void init() {
		Iterator<?> it = list.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof Initialisable) {
				((Initialisable)obj).init(context);
			}
		}
	}
}
