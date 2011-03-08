package org.stringtree.juicer.tract;

import org.stringtree.Tract;
import org.stringtree.juicer.JuicerLockHelper;

public class PoliteFilter extends BasicTractFilter {
    
	private BasicTractFilter realFilter;
	
	public PoliteFilter(BasicTractFilter filter) {
		this.realFilter = filter;
	}

	public Tract filter(Tract ret) {
		if (ret != null && !JuicerLockHelper.isLocked(ret)) {
			return realFilter.filter(ret);
		}

		return ret;
	}
}
