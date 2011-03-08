package org.stringtree.juicer.tract;

import org.stringtree.Fetcher;
import org.stringtree.juicer.AugmentedTransformation;
import org.stringtree.juicer.string.StringFilter;

public class AugmentedStringFilterTractFilter extends StringFilterTractFilter
	implements AugmentedTransformation {
    
	public AugmentedStringFilterTractFilter(StringFilter filter, boolean polite) {
		super(filter, polite);
	}

	public AugmentedStringFilterTractFilter(StringFilter filter) {
		super(filter);
	}

	public AugmentedStringFilterTractFilter() {
		super();
	}

	public void setAugment(Fetcher augment) {
		if (filter instanceof AugmentedTransformation) {
			((AugmentedTransformation)filter).setAugment(augment);
		}
	}
}
