package org.stringtree.http;

import java.util.List;

public interface Payload {
	String getHeader(String name);
	List<NameValue> getAllHeaders();
	byte[] getContentAsBytes();
	String getContentAsString();
	String getName();
}
