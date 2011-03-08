package org.stringtree.xml;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BasicTagHandler implements TagHandler {
	private Tag tag;	
	private Map<String, TagHandler> handlers;
	private XMLProcessor proc;	
	private XMLProcessorContext context;
	
	public BasicTagHandler(Tag tag) {
		this.tag = tag;
		handlers = new HashMap<String, TagHandler>();
		context = new XMLProcessorContext(tag.getWriter());
		context.setHandlers(handlers);
		proc = new XMLProcessor(context);
	}
	
	public BasicTagHandler(String name) {
		this(new Tag(name));
	}
	
	public Object doPair(Object context, String name, Map<String, String> args, Reader in) throws IOException {
		clearCdata();
		tag.setArguments(args);
		proc.run(context, in, name);
		
		return tag;
	}
	
	public Object doSingle(Object context, String name, Map<String, String> args) {
		tag.setArguments(args);
		
		return tag;
	}
	
	public Tag getTag() {
		return tag;
	}
	
	public String getArgument(String name) {
		return tag.getArgument(name);
	}
	
	public Iterator<String> getArgumentNames() {
		return tag.getArgumentNames();
	}
	
	public String getName() {
		return tag.getName();
	}

	public void setName(String name) {
		tag.setName(name);
	}

	public String getCdata() {
		return tag.getData();
	}

	public String getTrimmedData() {
		return tag.getTrimmedData();
	}

	public void setCdata(String data) {
		tag.setData(data);
	}

	public void clearCdata() {
		tag.setData("");
	}
		
	
	public void setHandler(String name, TagHandler handler) {
		handlers.put(name, handler);
	}
	
	public void addHandler(TagHandler handler) {
		handlers.put(handler.getName(), handler);
	}

	public void setDefaultHandler(TagHandler handler) {
		context.setDefaultHandler(handler);
	}
}