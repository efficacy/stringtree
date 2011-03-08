package org.stringtree.util;

import java.util.Date;

class CacheStatus extends Status {
    
    public CacheStatus() {
        super(Cached.EMPTY);
    }

    public CacheStatus(int initial) {
        super(initial);
    }
}

public abstract class AbstractCache<T> implements Cached<T> {
    
    protected CacheStatus status = new CacheStatus();
    protected Date timestamp = new Date(0);
    protected T cache;

    public AbstractCache(T cache) {
        this.cache = cache;
        markFull();
    }

    public AbstractCache() {
        this.cache = null;
        markEmpty();
    }

    public int getCachedStatus() {
        return status.get();
    }

    protected Object getObject() {
        return cache;
    }

    public T getValue() {
        ensure();
        return cache;
    }

    public java.util.Date getTimestamp() {
        return timestamp;
    }

    private synchronized void mark(int status, long stamp) {
        this.status.set(status);
        timestamp.setTime(stamp);
    }

    private void markFull() {
        mark(FULL, System.currentTimeMillis());
    }

    private void markEmpty() {
        mark(EMPTY, 0);
    }

    private void markLoading() {
        status.set(LOADING);
    }

    private void markError() {
        mark(ERROR, 0);
    }

    public void load() {
        if (status.get() != LOADING && status.get() != ERROR) {
            markLoading();
            try {
                if (doLoad()) {
                    markFull();
                } else {
                    markError();
                }
            } catch (Error e) {
                markError();
                throw (e);
            }
        }
    }

    public void unload() {
        doUnload();
        markEmpty();
    }

    public void ensure() {
        if (status.get() == EMPTY) {
            load();
        }
    }

    public void reload() {
        unload();
        load();
    }

    protected abstract boolean doLoad();
   protected abstract void doUnload();
}