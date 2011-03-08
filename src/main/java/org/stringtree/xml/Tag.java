package org.stringtree.xml;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

public class Tag implements Cloneable {
	private String name = null;
	private Map<String, String> args = null;
	private StringWriter writer = null;
	
	public Tag(String name, Map<String, String> args, String string) {
		this.name = name;
		this.args = args;
		this.writer = new StringWriter();
		
		if (string != null) {
			setData(string);
		}
	}
	
	public Tag(String name) {
		this(name, null, "");
	}
	
	public String getArgument(String name) {
		return args.get(name);
	}
	
	public Iterator<String> getArgumentNames() {
		return args.keySet().iterator();
	}
	
	public void setArguments(Map<String, String> args) {
		this.args = args;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData() {
		return writer.toString();
	}
	
	/**
	 note that this is a relatively expensive operation, 
	 so please consider cacheing the result
	*/
	public String getTrimmedData() {
		String old = getData();
		StringBuffer buf = new StringBuffer();
		
		boolean atStart = true;		
		int len = old.length();
		for (int i = 0; i < len; ++i) {
			char c = old.charAt(i);
			
			if (c == '\n') {
				continue;
			}
			
			if (atStart && Character.isSpaceChar(c)) {
				continue;
			}
			
			buf.append(c);
			atStart = false;
		}
		
		len = buf.length();
		for (int i = len-1; Character.isSpaceChar(buf.charAt(i)); --i) {
			--len;
		}
			
		buf.setLength(len); 		
		return buf.toString();
	}
	
	public Writer getWriter() {
		return writer;
	}

	public void setData(String string) {
		writer.getBuffer().setLength(0);
		writer.getBuffer().append(string);
	}
	
	public String show() {
		return "tag: '" + name + "' cdata='" + getData() + "'";
	}
	
	public Object clone() {
	    try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return new Tag(name, args, writer.toString());
        }
	}
}