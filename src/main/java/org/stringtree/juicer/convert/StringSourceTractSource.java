package org.stringtree.juicer.convert;

import org.stringtree.Tract;
import org.stringtree.juicer.string.StringSource;
import org.stringtree.juicer.tract.TractSource;
import org.stringtree.tract.MapTract;

public class StringSourceTractSource implements TractSource {
	private StringSource source;

	public StringSourceTractSource(StringSource source) {
		this.source = source;
	}

	public Tract nextTract() {
		String ret = source.nextString();
		return ret != null ? new MapTract(ret) : null;
	}
}
