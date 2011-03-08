package org.stringtree.juicer.tract;

import org.stringtree.Tract;
import org.stringtree.juicer.JuicerLockHelper;
import org.stringtree.tract.MapTract;

public abstract class ReplaceTractFilter extends BasicTractFilter {
    
	protected abstract boolean recognize(String content);
	protected abstract String convert(String content);

	protected boolean polite;
	
	public ReplaceTractFilter(boolean polite) {
		this.polite = polite;
	}
	
	public ReplaceTractFilter() {
		this(true);
	}
	
	public Tract filter(Tract tract) {
		if (polite && JuicerLockHelper.isLocked(tract)) {
			return tract;
		}

		Tract ret = tract;
		
		String content = tract.getContent();
		if (recognize(content)) {
			ret = new MapTract(tract);
			ret.setContent(convert(content));
		}

		return ret;
	}
}
