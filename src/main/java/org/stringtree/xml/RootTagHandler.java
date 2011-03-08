package org.stringtree.xml;

import java.io.IOException;
import java.io.Reader;

public class RootTagHandler extends BasicTagHandler {
	public RootTagHandler() {
		super((String)null);
	    setDefaultHandler(this);
	}

	public void run(Object context, Reader in) throws IOException {
		super.doPair(context, null, null, in);
	}
}