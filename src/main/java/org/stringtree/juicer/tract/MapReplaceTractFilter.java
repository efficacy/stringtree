package org.stringtree.juicer.tract;

import java.util.Map;

public class MapReplaceTractFilter extends ReplaceTractFilter {
    
	protected Map<String,String> map;
	
	public MapReplaceTractFilter(Map<String,String> map, boolean polite) {
		super(polite);
		this.map = map;
	}
	
	public MapReplaceTractFilter(Map<String,String> map) {
		this.map = map;
	}

	protected boolean recognize(String content) {
		return map.containsKey(content);
	}

	protected String convert(String content) {
		return (String)map.get(content);
	}
}
