package org.stringtree.juicer.string;

import java.util.Arrays;
import java.util.List;

import org.stringtree.juicer.JuicerStringHelper;

public class StringPipeline extends PassStringFilter {
    
	private StringSource head;
	protected List<StringFilter> list;
	
	public StringPipeline(StringFilter[] array, StringSource source) {
		super(source);
		setList(Arrays.asList(array));
	}

	public StringPipeline(StringSource source) {
		super(source);
	}
	
	public StringPipeline(StringFilter[] array) {
		setList(Arrays.asList(array));
	}

	public StringPipeline() {
		// this method intentionally left blank
	}
	
	public void relink() {
		source = JuicerStringHelper.linkStringFilters(list, head);
	}
	
	public void setList(List<StringFilter> list) {
		this.list = list;
		if (head != null) {
			relink();
		}
	}

	public void connectSource(StringSource source) {
		this.head = source;
		if (list != null) {
			relink();
		}
	}
}
