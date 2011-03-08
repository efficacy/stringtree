package tests.workflow;

import org.stringtree.workflow.BasicSideEffect;
import org.stringtree.workflow.StateMachine;

public class RedirectEffect extends BasicSideEffect {
    
	public boolean execute(String from, String code, String to, Object context) {
		code = (String)params;
		if (verbose) System.out.print("Warning, machine redirected using code '" + code + "'");
		if (context instanceof StateMachine) {
			StateMachine machine = (StateMachine)context;
			machine.next(code);
			if (verbose) System.out.println(" to state '" + machine.getState() + "'");
		}

		return false;
	}
}