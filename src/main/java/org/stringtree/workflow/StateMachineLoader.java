package org.stringtree.workflow;

import java.io.Reader;
import java.io.IOException;

public interface StateMachineLoader {
    
	public void load(StateMachineSpec spec, Reader reader)
		throws IOException;
}