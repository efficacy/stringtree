package org.stringtree.workflow;

import java.util.StringTokenizer;

/**
 Visitor to load a StateMachine from a simple text file.

 The definition file has the following format:
 <pre>
 + lines starting with '#' are treated as comments and ignored
 + the first non-comment line contains a single token - the name of the initial state
 + further non-comment lines define the state transitions and consist of three elements,
   in order, separated by one or more spaces
   1. the state from which this transition is valid
   2. the symbol which represents this transition
   3. the destination state or special operation symbol
 </pre>
 The following default special symbols are defined:
 <pre>
 _BACK  return to the preceding state in the state history
 _RESET  return to the initial state
 _EXIT  finish the state machine and return null for all future transitions
 </pre>

 The transition symbol "=" is special cased to imply that the source state inherits
 all the transitions of the destination state.  These transitions may be overridden by
 later definitions.

 As an example, here is a definition file for a simple fault classification system:
 <pre>
 # fault classification system
 #
 # all faults start as:
 UNKNOWN
 #
 # and the following transitions are valid
 # state      transition  destination
 # ----------------------------------
 :ALL         REJECT      REJECTED
 UNKNOWN      = :ALL
 UNKNOWN      ACCEPT      DEVELOPMENT
 DEVELOPMENT  = :ALL
 DEVELOPMENT  RELEASE     TEST
 TEST         PASS        DELIVERY
 TEST         FAIL        DEVELOPMENT
 </pre>
*/
public class ClassicMachineLoader extends LineBasedMachineLoader {
    
	private void command(StateMachineSpec spec, String command, StringTokenizer tok) {
		if ("$START".equals(command)) {
			spec.setInitialState(head(tok));
		} else if ("$BACKSYMBOL".equals(command)) {
			spec.setBackSymbol(head(tok));
		} else if ("$RESETSYMBOL".equals(command)) {
			spec.setResetSymbol(head(tok));
		} else if ("$SELFSYMBOL".equals(command)) {
			spec.setSelfSymbol(head(tok));
		}
	}

	public void addLine(StateMachineSpec spec, String line) {
		StringTokenizer tok = new StringTokenizer(line);

		if (line.startsWith("$")) {
			command(spec, head(tok), tok);
		} else {
			String state = head(tok);
			String code = head(tok);
			String dest = head(tok);

			if ("=".equals(code)) {
				spec.cloneState(dest, state);
			} else if (tok.hasMoreTokens()) {
				String effect = head(tok);
				String params = tail(tok);

				spec.addDestination(state, code, new Transition(dest, effect, params));
			} else {
				spec.addDestination(state, code, dest);
			}
		}
	}
}