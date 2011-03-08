package org.stringtree.juicer.tract;

import org.stringtree.Tract;

public interface TractFilter extends TractSource, TractDestination {
	Tract filter(Tract Tract);
}
