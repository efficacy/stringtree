package org.stringtree.tract;

import java.util.HashMap;

import org.stringtree.Tract;
import org.stringtree.util.StringUtils;

public class MapByteTract extends MapTract implements ByteTract {
    
    public MapByteTract() {
        super("", new HashMap<String, Object>());
    }

    public void setContent(String content) {
        put(Tract.CONTENT, content.getBytes());
    }

    public String getContent() {
        Object ret = getObject(Tract.CONTENT);
        if (ret instanceof byte[]) return new String((byte[])ret);
        return StringUtils.stringValue(ret);
    }
    
    public byte[] getContentBytes() {
        Object ret = getObject(Tract.CONTENT);
        if (ret == null || !(ret instanceof byte[])) {
            ret = new byte[] {};
        }
        return (byte[])ret;
    }

    public void setContentBytes(byte[] bytes) {
        put(Tract.CONTENT, bytes);
    }
}
