package org.stringtree.finder.live;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.stringtree.fetcher.LiveObjectWrapper;
import org.stringtree.finder.StringFinder;
import org.stringtree.util.StringUtils;

public class LiveDate implements LiveObjectWrapper {
    
    public static final String DATE_FORMAT = "system.date.format";

    protected DateFormat formatter;

    public LiveDate() {
        this.formatter = null;
    }

    public LiveDate(DateFormat formatter) {
        this.formatter = formatter;
    }

    public LiveDate(String format) {
        setFormat(format);
    }

    public void init(StringFinder context) {
        String format = context.get(DATE_FORMAT);
        if (!StringUtils.isBlank(format)) {
            setFormat(format);
        }
    }

    public void setFormat(String format) {
        this.formatter = new SimpleDateFormat(format);
    }

    public Object getObject() {
        Object object = getRaw();

        if (formatter != null && object instanceof Date) {
            return formatter.format((Date) object);
        }

        return object;
    }

    public Object getRaw() {
        return new Date();
    }
    
    public String toString() {
        return getObject().toString();
    }
}
