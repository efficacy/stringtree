package tests.workflow;

import org.stringtree.workflow.BasicSideEffect;
import org.stringtree.workflow.StateMachine;

public class ForceEffect extends BasicSideEffect {
    
	public boolean execute(String from, String code, String to, Object context) {
		String message = "Warning, machine reset";
		if (verbose) System.out.println(message);
		if (context instanceof StateMachine) {
			((StateMachine)context).reset();
		}

		return false;
	}
}