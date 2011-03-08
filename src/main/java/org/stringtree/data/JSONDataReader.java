package org.stringtree.data;

import org.stringtree.finder.StringFinder;
import org.stringtree.json.JSONReader;

public class JSONDataReader implements DataReader {

	private JSONReader json;
	private String key;

	public JSONDataReader(JSONReader json) {
		this.json = json;
        this.key = null;
	}

    public JSONDataReader() {
        this(new JSONReader());
    }

	public JSONDataReader(String key) {
		this.key = key;
	}
	
	public void init(StringFinder context) {
		this.json = (JSONReader)context.getObject(key);
	}

	public Object read(String data) {
		return json.read(data);
	}
}
