package org.stringtree.mock;

import java.util.Arrays;

import org.stringtree.util.StringUtils;
import org.stringtree.util.Utils;

public class MockAction {
    
	public Object destination;
	public String message;
	public Object[] arguments;

	public MockAction(Object destination, String message, Object... arguments) {
		this.destination = destination;
		this.message = message;
		this.arguments = arguments;
	}

	public MockAction(Object destination, String message) {
        this.destination = destination;
        this.message = message;
        this.arguments = null;
	}
	
	public String toString() {
		String objectClause = StringUtils.isBlank(destination) ? "" : "' on object '" + destination + "'";
        return "calling '" + message + objectClause + " with args (" + expandArgs() + ")";  
	}

	public String expandArgs() {
		if (arguments == null) return "";
		int last = arguments.length-1;
		StringBuffer ret = new StringBuffer();
		
		for (int i = 0; i < last; ++i) {
			ret.append(arguments[i]);
			ret.append(", ");
		}
		ret.append(arguments[last]);
		
		return ret.toString();
	}
    
    public String asCall() {
        return message + "(" + expandArgs() + ")";
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof MockAction)) return false;
        MockAction other = (MockAction)obj;
        
        return
            Utils.same(destination, other.destination) &&
            Utils.same(message, other.message) &&
            Arrays.equals(arguments, other.arguments);
    }
    
    @Override
    public int hashCode() {
        return destination.hashCode() + message.hashCode() + Arrays.hashCode(arguments);
    }
}
