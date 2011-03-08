package org.stringtree.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MultipartForm extends LinkedHashMap<String, Payload> implements Payload {
    public static final String MULTPART = "multipart/form-data; boundary=";
    private String boundary;
	private List<NameValue> headers; 
    
    public MultipartForm(String boundary) {
    	this.boundary = boundary;
    	headers = Arrays.asList(
        		new NameValue(Document.CONTENT_TYPE, getContentType())
        	);
    }
    
    public MultipartForm() {
    	this(randomBoundary());
    }
    
    private static String randomBoundary() {
		return "----------" + (long)(Math.random() * 1000000000000L);
	}

    public String toString() {
        return getContentAsString();
    }
    
    private byte[] spool(Map<String, Payload> map) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bytes);

        for (Map.Entry<String, Payload> entry : map.entrySet()) {
        	String name = entry.getKey();
        	Payload payload = entry.getValue();
        	
        	ps.print("--");
        	ps.print(boundary);
        	ps.print("\r\n");

        	ps.print("Content-Disposition: form-data; name=\"");
        	ps.print(name);
        	ps.print("\"");
        	String fn = payload.getName();
        	if (null != fn) {
        		ps.print("; filename=\"");
        		ps.print(fn);
        		ps.print("\"");
        	}
        	ps.print("\r\n");

        	for (NameValue header : payload.getAllHeaders()) {
				ps.print(header.name);
				ps.print(": ");
				ps.print(header.value);
	        	ps.print("\r\n");
			}
        	ps.print("\r\n");
        	ps.write(payload.getContentAsBytes());
			
        	ps.print("\r\n");
        }
        
    	ps.print("--");
    	ps.print(boundary);
    	ps.print("--");
    	ps.print("\r\n");

    	ps.flush();
        return bytes.toByteArray();
    }

	@Override
	public List<NameValue> getAllHeaders() {
		return headers;
	}

	@Override
	public byte[] getContentAsBytes() {
		try {
			return spool(this);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getContentAsString() {
		try {
			return new String(spool(this));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getHeader(String name) {
		return Document.CONTENT_TYPE.equals(name) ? getContentType() : null;
	}

	public String getContentType() {
		return MULTPART + boundary;
	}
	
	public String dump() {
		StringBuffer ret = new StringBuffer();
		
		for (NameValue nv : getAllHeaders()) {
			ret.append(nv.name);
			ret.append(": ");
			ret.append(nv.value);
			ret.append("\r\n");
		}
		ret.append("\r\n");
		ret.append(getContentAsString());
		
		return ret.toString();
	}

	@Override
	public String getName() {
		return null;
	}
	
	public void addPart(String name, Payload part) {
		put(name, part);
	}
	
	public void addPart(String name, String part) {
		put(name, new Document(part));
	}
	
	public void addPart(String name, byte[] part) {
		put(name, new Document(part));
	}
	
	public void addPart(String name, File part) {
		put(name, new FileDocument(part));
	}
}
