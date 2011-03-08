package org.stringtree.http;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Document implements Payload {
	public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
	public static final String CONTENT_LENGTH = "Content-Length";
    public static final String USER_AGENT = "User-Agent";
    public static final String CONTENT_TYPE = "Content-Type";
    
    protected List<NameValue> headers = new ArrayList<NameValue>();
    protected byte[] content;
    
    public Document(byte[] content, String type) {
        setContent(content, type);
    }
    
    public Document(byte[] content) {
    	setContent(content);
    }
    
    public Document(String content, String type) {
        setContent(content.getBytes(), type);
    }
    
    public Document(String content) {
        setContent(content);
    }
    
    public Document() {
    }

    private void addHeader(NameValue header) {
        headers.add(header);
    }

    public void addHeader(String name, String value) {
        headers.add(new NameValue(name, value));
    }

    public void setHeader(String name, String value) {
        NameValue header = new NameValue(name, value);
        
        if (headers.contains(header)) {
            headers.remove(header);
        }
        
        addHeader(header);
    }
    
    public String getHeader(String name) {
        Iterator<NameValue> it = headers.iterator();
        while (it.hasNext()) {
            NameValue nv = (NameValue)it.next();
            if (name.equals(nv.name)) return nv.value;
        }
        
        return null;
    }
    
    public List<NameValue> getAllHeaders(String name) {
        List<NameValue> ret = new ArrayList<NameValue>();
        Iterator<NameValue> it = headers.iterator();
        while (it.hasNext()) {
            NameValue nv = it.next();
            if (name.equals(nv.name)) ret.add(nv);
        }
        return ret;
    }
    
    public List<NameValue> getAllHeaders() {
        return headers;
    }
    
    public void setContent(String content, String type) {
        addHeader(CONTENT_TYPE, type);
        addHeader(CONTENT_TRANSFER_ENCODING, "8bit");
        this.content = null == content
            ? (byte[])null
            : content.getBytes();
    }
    
    public void setContent(String content) {
    	setContent(content, "text/plain; charset=US-ASCII");
    }
    
    public void setContent(byte[] content, String type) {
        addHeader(CONTENT_TYPE, type);
        addHeader(CONTENT_TRANSFER_ENCODING, "binary");
        this.content = content;
        if (null != content) headers.add(new NameValue(CONTENT_LENGTH, Integer.toString(content.length)));
    }
    
    public void setContent(byte[] content) {
    	setContent(content, "application/octet-stream; charset=ISO-8859-1");
    }
    
    public String getContentAsString() {
        return new String(content);
    }
    
    public byte[] getContentAsBytes() {
        return content;
    }
    
    public String toString() {
        return "Document[" + dump(headers) + "\n" + getContentAsString() + "]";
    }

    private StringBuffer dump(List<NameValue> list) {
        StringBuffer ret = new StringBuffer("[");
        Iterator<NameValue> it = list.iterator();
        while (it.hasNext()) {
            NameValue nv = (NameValue)it.next();
            ret.append(" ");
            ret.append(nv.name);
            ret.append("=");
            ret.append(nv.value);
        }
        ret.append(" ]");
        return ret;
    }

	@Override
	public String getName() {
		return null;
	}
}
