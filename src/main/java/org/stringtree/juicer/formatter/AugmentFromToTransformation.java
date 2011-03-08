package org.stringtree.juicer.formatter;

import org.stringtree.Fetcher;
import org.stringtree.juicer.AugmentedTransformation;
import org.stringtree.juicer.string.SplitStringFilter;
import org.stringtree.juicer.string.StringStringSource;
import org.stringtree.util.ClassUtils;
import org.stringtree.util.StringUtils;

public abstract class AugmentFromToTransformation extends FromToTransformation {
    
	public void init(String tail) {
		SplitStringFilter split = new SplitStringFilter();
		split.connectSource(new StringStringSource(tail));
		String augmentClass = split.nextString();

		split.setSeparator('\0');
		super.init(split.nextString());
			
		if (!StringUtils.isBlank(augmentClass) && other instanceof AugmentedTransformation) {
			Fetcher augmentObject = (Fetcher)ClassUtils.createObject(augmentClass);
			if (augmentObject != null) {
				((AugmentedTransformation)other).setAugment(augmentObject);
			} else {
				System.out.println("could not create augment class '" + augmentClass + "'");
			}
		}
	}
}
