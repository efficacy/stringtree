package org.stringtree.juicer.convert;

import org.stringtree.juicer.JuicerConvertHelper;
import org.stringtree.juicer.string.StringSource;
import org.stringtree.juicer.tract.EmptyTractSource;
import org.stringtree.juicer.tract.TractDestination;
import org.stringtree.juicer.tract.TractSource;

public class TractStringFunnel implements TractDestination, StringSource {
    
	protected TractSource source;

	public TractStringFunnel(TractSource source) {
		connectSource(source);
	}

	public TractStringFunnel() {
		this(EmptyTractSource.it);
	}

	public void connectSource(TractSource source) {
		this.source = source;
	}
	
	public String nextString() {
		return JuicerConvertHelper.compress(source.nextTract());
	}
}
