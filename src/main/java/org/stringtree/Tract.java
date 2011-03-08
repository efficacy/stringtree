package org.stringtree;

import org.stringtree.finder.StringKeeper;

public interface Tract extends StringKeeper, Listable<String>, Container {
    static final String CHARSET = "charset";
    static final String NAME = "~name";
    static final String DATE = "~date";
    static final String CONTENT_LOCATION = "content-location";
    static final String CONTENT = "CONTENT";
    static final String INDIRECT_ROOT = "indirect.root";

    void setContent(String content);
    String getContent();
    boolean hasContent();
    String get(String name, String dfl);
    String getCharacterSet();
}
