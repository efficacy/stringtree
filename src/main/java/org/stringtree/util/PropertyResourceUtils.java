package org.stringtree.util;

import java.util.Map;

public class PropertyResourceUtils {
    
    public static Map<String, Object> readPropertyResource(ClassLoader loader, String filename) {
        return PropertyStreamUtils.readProperties(ResourceUtils.getResourceStream(loader, filename));
    }

    public static Map<String, Object> readPropertyResource(Object self, String filename) {
        return readPropertyResource(self.getClass().getClassLoader(), filename);
    }

    public static Map<String, Object> readPropertyResource(String filename) {
        return readPropertyResource(ResourceUtils.class.getClassLoader(),filename);
    }
}
