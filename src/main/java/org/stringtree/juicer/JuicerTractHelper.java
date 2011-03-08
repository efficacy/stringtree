package org.stringtree.juicer;

import java.util.List;

import org.stringtree.juicer.tract.TractFilter;
import org.stringtree.juicer.tract.TractSource;

public class JuicerTractHelper {
    
	public static TractSource linkTractFilters(List<TractFilter> list, TractSource head) {
		TractSource tail = head;
		for (TractFilter filter : list) {
			filter.connectSource(tail);
			tail = filter;
		}
		
		return tail;
	}
}
