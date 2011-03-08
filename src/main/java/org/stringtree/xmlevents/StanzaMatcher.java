package org.stringtree.xmlevents;

import java.util.Map;

public interface StanzaMatcher {
	Object match(String path, Map<?,?> values, Object context);
}
