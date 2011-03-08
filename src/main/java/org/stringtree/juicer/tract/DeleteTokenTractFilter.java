package org.stringtree.juicer.tract;

import org.stringtree.Tract;

public class DeleteTokenTractFilter extends BasicTractFilter {
    
	public Tract nextTract() {
		Tract tract = source.nextTract();
		
		while (tract != null && TokenHelper.isToken(tract)) {
			tract = source.nextTract();
		}

		return tract;
	}
}
