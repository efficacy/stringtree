package org.stringtree.data;

public class AgnosticDataReader implements DataReader {

    private JSONDataReader json = new JSONDataReader();
    private XMLDataReader xml = new XMLDataReader();
    
    public Object read(String data) {
        Object ret = null;
        if (null != data) {
            data = data.trim();
            
            if (data.startsWith("<")) {
                ret = xml.read(data);
            } else {
                ret = json.read(data);
            }
        }
        return ret;
    }

}
