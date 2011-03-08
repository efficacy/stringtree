package org.stringtree.workflow;

import java.util.StringTokenizer;
import java.util.Stack;

/**
 Visitor to load a StateMachine from a hierarchical text file.
 
 The definition file has the following format:
 <pre>
 + lines starting with '#' are treated as comments and ignored
 +
 
 As an example, here is a definition file for a simple image generator system:
 <pre>
 # image generator
 # ----------------------------------
 image
  template
  margins
   top
   bottom
   left
   right
  content
   text
   image
 </pre>
*/
public class HierarchyMachineLoader extends LineBasedMachineLoader {
    
	private static final String INITIAL = "";
	private Stack<String> history = new Stack<String>();
	
	public void init(StateMachineSpec spec) {
		super.init(spec);
		spec.setInitialState(INITIAL);
		history.clear();
		history.push(INITIAL);
	}
	
	public void addLine(StateMachineSpec spec, String line) {
		int depth = history.size() - 2;
		
		int indent = 0;
		while (indent < line.length() && Character.isWhitespace(line.charAt(indent))) {
			++indent;
		}

		if (indent == depth) {
			history.pop();
		} else if (indent < depth) {
			history.pop();
			history.pop();
		}

		String source = history.peek();
		line = line.substring(indent);

		StringTokenizer tok = new StringTokenizer(line);
		String tx = head(tok);
		String dest = source + "." + tx;

		addDestination(spec, source, tx, dest, head(tok), tail(tok));
		
		history.push(dest);
	}
}