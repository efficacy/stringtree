package org.stringtree.streams;

import org.stringtree.Fetcher;

public class ValueObjectFactory implements ObjectFactory {

    private Object object;

    public ValueObjectFactory(Object object) {
        this.object = object;
    }

    public Object create(Fetcher context) {
        return object;
    }

}
