package tests.workflow;

import java.util.Map;

public class EchoEffect extends org.stringtree.workflow.BasicSideEffect {
    
	@SuppressWarnings("unchecked")
    public boolean execute(String from, String code, String to, Object context) {
		String message = "executing tx from '" + from + "' via '" + code + "' to '" + to + "' (" + params + ")";
		if (verbose) System.out.println(message);
		if (context instanceof Map) {
			((Map)context).put("message", message);
		}
		
		return true;
	}
}