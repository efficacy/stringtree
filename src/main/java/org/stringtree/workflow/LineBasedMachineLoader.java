package org.stringtree.workflow;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

public abstract class LineBasedMachineLoader implements StateMachineLoader {
    
	private static final String comment = "#";
	private int lineNumber;
	
	protected static boolean isblank(String line) {
		return line == null || "".equals(line.trim());
	}
	
	protected static String head(StringTokenizer tok) {
		return tok.hasMoreTokens() ? tok.nextToken() : null;
	}

	protected static String tail(StringTokenizer tok) {
		StringBuffer ret = new StringBuffer();
		boolean atStart = true;
		
		while (tok.hasMoreTokens()) {
			if (!atStart) {
				ret.append(" ");
			}
			ret.append(tok.nextToken());
			atStart = false;
		}
		
		return ret.toString();	
	}

	public void addDestination(StateMachineSpec spec, String source, String code, 
		String dest, String effect, String param) {
		if (isblank(effect)) {
			spec.addDestination(source, code, dest);
		} else {
			spec.addDestination(source, code, new Transition(dest, effect, param));
		}
	}

	public abstract void addLine(StateMachineSpec spec, String line);
	
	protected void init(StateMachineSpec spec) {
		lineNumber = 0;
	}
	
	public void load(StateMachineSpec spec, Reader reader) throws IOException {
		BufferedReader in = null;
		
		init(spec);

		try {
			in = new BufferedReader(reader);

			for (String line = in.readLine(); line != null; line = in.readLine()) {
				if (isblank(line) || line.startsWith(comment)) {
					continue;
				}

				++lineNumber;
				addLine(spec, line);
			}
		} finally {
			if (null != in) in.close();
		}
	}

	public int getLineNumber() {
		return lineNumber;
	}
}