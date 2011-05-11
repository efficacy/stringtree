package tests.workflow;

import java.util.Map;

public class VetoEffect extends org.stringtree.workflow.BasicSideEffect {
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public boolean execute(String from, String code, String to, Object context) {
		String message = "Warning, no state change";
		if (verbose) System.out.println(message);
		if (context instanceof Map) {
			((Map)context).put("message", message);
		}

		return false;
	}
}