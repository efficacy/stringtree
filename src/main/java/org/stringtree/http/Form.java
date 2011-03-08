package org.stringtree.http;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.stringtree.util.URLUtils;

public class Form extends LinkedHashMap<String, String> implements Payload {
    public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    
    private List<NameValue> headers = Arrays.asList(
    		new NameValue(Document.CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED)
    	);

    public String toString() {
        return spool(this);
    }
    
    private static String spool(Map<String, String> map) {
        boolean had = false;
        StringBuffer form = new StringBuffer();
        
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (had) {
                form.append("&");
            }
            form.append(URLUtils.escape(entry.getKey()));
            form.append("=");
            form.append(URLUtils.escape(entry.getValue()));
            had = true;
        }
        
        return form.toString();
    }

	@Override
	public List<NameValue> getAllHeaders() {
		return headers;
	}

	@Override
	public byte[] getContentAsBytes() {
		return spool(this).getBytes();
	}

	@Override
	public String getContentAsString() {
		return spool(this);
	}

	@Override
	public String getHeader(String name) {
		return Document.CONTENT_TYPE.equals(name) ? APPLICATION_X_WWW_FORM_URLENCODED : null;
	}

	@Override
	public String getName() {
		return null;
	}
}
