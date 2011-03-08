package org.stringtree.util;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class LiteralMap<K,V> extends LinkedHashMap<K, V> {
	@SuppressWarnings("unchecked")
	public LiteralMap(Object... objects) {
		if (objects.length % 2 != 0) throw new IllegalArgumentException("LiteralMap requires an even number of args");
		for (int i = 0; i < objects.length; i += 2) {
			put((K)objects[i], (V)objects[i+1]);
		}
	}

}
