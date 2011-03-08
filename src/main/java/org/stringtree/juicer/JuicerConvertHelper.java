package org.stringtree.juicer;

import org.stringtree.Tract;
import org.stringtree.juicer.string.StringSource;
import org.stringtree.juicer.tract.TractSource;
import org.stringtree.tract.MapTract;

public class JuicerConvertHelper {
    
	public static Tract expand(String string) {
		Tract ret = null;
		
		if (string != null) {
			ret = new MapTract(string);
		}
		
		return ret;
	}

	public static Tract expand(String string, Tract tract) {
		Tract ret = null;
		
		if (string != null) {
			ret = new MapTract(string, tract);
		}
		
		return ret;
	}
	
	public static String compress(Tract tract) {
		String ret = null;
		
		if (tract != null) {
			ret = tract.getContent();
		}
		
		return ret;
	}

	public static String nextString(TractSource source) {
		return compress(source.nextTract());
	}

	public static Tract nextTract(StringSource source) {
		return expand(source.nextString());
	}
}
