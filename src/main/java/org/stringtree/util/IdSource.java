package org.stringtree.util;

public interface IdSource {
	String next();
	boolean valid(String id);
}
