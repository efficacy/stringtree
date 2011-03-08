package org.stringtree.util.spec;

import java.io.IOException;

import org.stringtree.util.StringUtils;

public abstract class SimpleOrIncludeHandler extends SimpleHandler {

    public Object parse(String name, Object value) {
        if (StringUtils.isBlank(name) && value instanceof String) {
            try {
                include((String)value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return SpecProcessor.NO_ACTION;
        }
        
        return super.parse(name, value);
    }

    protected abstract void include(String name) throws IOException;
}
