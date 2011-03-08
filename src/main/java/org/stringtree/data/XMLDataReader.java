package org.stringtree.data;

import org.stringtree.finder.StringFinder;
import org.stringtree.xml.XMLReader;

public class XMLDataReader implements DataReader {

	private XMLReader xml;
	private String key;

	public XMLDataReader(XMLReader xml) {
		this.xml = xml;
        this.key = null;
        xml.setIgnoreRoot(true);
	}

    public XMLDataReader() {
        this(new XMLReader());
    }

	public XMLDataReader(String key) {
		this.key = key;
	}
	
	public void init(StringFinder context) {
		this.xml = (XMLReader)context.getObject(key);
	}

	public Object read(String data) {
		return xml.read(data);
	}
}
