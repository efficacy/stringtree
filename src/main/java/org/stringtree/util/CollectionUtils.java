package org.stringtree.util;

import java.util.Collection;

public class CollectionUtils {
    
    public static Object getFirstItem(Collection<?> collection) {
        return null == collection || collection.isEmpty()
            ? null
            : collection.iterator().next();
    }
}
