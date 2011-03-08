package org.stringtree.util;

import java.util.Date;

public interface Cached<T> extends Proxy<T> {
    static final int FULL = 0;
    static final int EMPTY = 1;
    static final int LOADING = 2;
    static final int ERROR = 3;

    int getCachedStatus();
    Date getTimestamp();
    void load(); // load if not already LOADING or ERROR
    void unload(); // clear data and set status to EMPTY
    void ensure(); // load only if EMPTY
    void reload(); // unload to clear ERROR status, then force a load
}