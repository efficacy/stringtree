package org.stringtree.template.plugin;

import org.stringtree.finder.StringFinder;

public interface Plugin {
	boolean match(StringFinder context);
}
