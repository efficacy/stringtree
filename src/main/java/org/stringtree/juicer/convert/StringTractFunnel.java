package org.stringtree.juicer.convert;

import org.stringtree.Tract;
import org.stringtree.juicer.JuicerConvertHelper;
import org.stringtree.juicer.string.EmptyStringSource;
import org.stringtree.juicer.string.StringDestination;
import org.stringtree.juicer.string.StringSource;
import org.stringtree.juicer.tract.TractSource;

public class StringTractFunnel implements StringDestination, TractSource {
    
	protected StringSource source;

	public StringTractFunnel(StringSource source) {
		connectSource(source);
	}

	public StringTractFunnel() {
		this(EmptyStringSource.it);
	}

	public void connectSource(StringSource source) {
		this.source = source;
	}
	
	public Tract nextTract() {
		return JuicerConvertHelper.nextTract(source);
	}
}
