package org.stringtree.juicer;

import java.util.List;

import org.stringtree.juicer.string.StringFilter;
import org.stringtree.juicer.string.StringSource;

public class JuicerStringHelper {
    
	public static StringSource linkStringFilters(List<StringFilter> list, StringSource head) {
		StringSource tail = head;
		for (StringFilter filter : list) {
			filter.connectSource(tail);
			tail = filter;
		}
		
		return tail;
	}
}
