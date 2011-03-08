package org.stringtree.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyStreamUtils {
    
    public static Map<String, Object> readProperties(InputStream in) {
        Properties props = new Properties();
        try {
            props.load(in);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try {
            if (in != null)
                in.close();
        } catch (IOException ioe) {
            ioe.printStackTrace(); // exception in close makes little sense
        }

        return convertProperties(props);
    }

    public static Map<String, Object> convertProperties(Properties props) {
        Map<String, Object> ret = new HashMap<String, Object>();
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            ret.put((String)entry.getKey(), entry.getValue());
        }
        return ret;
    }
}
