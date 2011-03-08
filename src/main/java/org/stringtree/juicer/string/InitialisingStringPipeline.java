package org.stringtree.juicer.string;

import java.util.Iterator;
import java.util.List;

import org.stringtree.Fetcher;
import org.stringtree.juicer.Initialisable;

public class InitialisingStringPipeline extends StringPipeline implements Initialisable {
    
	private Fetcher context;
	
	public InitialisingStringPipeline(StringFilter[] array, Fetcher context) {
		super(array);
		init(context);
	}

	public InitialisingStringPipeline(Fetcher context) {
		init(context);
	}
	
	public void relink() {
		super.relink();
		init();
	}
	
	public void init(Fetcher context) {
		this.context = context;
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
	
	public void setList(List<StringFilter> list) {
		super.setList(list);
		init();
	}
}
