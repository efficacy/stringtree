package org.stringtree.workflow;

import java.io.Reader;
import java.io.IOException;

public class StateMachineFactory {
    
	private StateMachineLoader loader;
	private StateMachineSpec spec;
		
	public StateMachineFactory(StateMachineLoader loader, StateMachineSpec spec) {
		this.loader = loader;
		this.spec = spec;
	}

	public StateMachineFactory(StateMachineLoader loader) {
		this(loader, new StateMachineSpec());
	}
	
	public void load(Reader source) throws IOException {
		spec.reset();
		loader.load(spec, source);
	}
	
	public StateMachine create() {
		return new StateMachine(spec);
	}
	
	public StateMachine create(Object context) {
		StateMachine ret = create();
		ret.setContext(context);
		return ret;
	}
}