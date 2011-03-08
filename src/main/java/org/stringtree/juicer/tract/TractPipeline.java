package org.stringtree.juicer.tract;

import java.util.Arrays;
import java.util.List;

import org.stringtree.juicer.JuicerTractHelper;

public class TractPipeline extends BasicTractFilter {
    
	private TractSource head;
	protected List<TractFilter> list;

	public TractPipeline(TractFilter[] array) {
		setList(Arrays.asList(array));
	}
	
	public TractPipeline() {
		// this method intentionally left blank
	}

	public void relink() {
		source = JuicerTractHelper.linkTractFilters(list, head);
	}
	
	public void setList(List<TractFilter> list) {
		this.list = list;
		if (head != null) {
			relink();
		}
	}

	public void connectSource(TractSource source) {
		this.head = source;
		if (list != null) {
			relink();
		}
	}
}
