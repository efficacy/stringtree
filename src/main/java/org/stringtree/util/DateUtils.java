package org.stringtree.util;

import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class DateUtils {
    
    protected static DateFormat dflFormat() {
        return new SimpleDateFormat("dd/MM/yyyy");
    }

    public static Date dateValue(Object obj, DateFormat df, Date dfl) {
        Date ret = dfl;

        if (obj != null) {
            if (obj instanceof Date) {
                ret = (Date) obj;
            } else if (obj instanceof Calendar) {
                ret = ((Calendar) obj).getTime();
            } else if (NumericValidator.isValidNumber(obj)) {
                ret = new Date(LongNumberUtils.longValue(obj));
            } else {
                try {
                    ret = df.parse(obj.toString());
                } catch (ParseException e) {
                    ret = dfl;
                }
            }
        }

        return ret;
    }

    public static Date dateValue(Object obj, String format, Date dfl) {
        return dateValue(obj, new SimpleDateFormat(format), dfl);
    }

    public static Date dateValue(Object s, DateFormat format) {
        return dateValue(s, format, new Date());
    }

    public static Date dateValue(Object s, String format) {
        return dateValue(s, format, new Date());
    }

    public static Date dateValue(Object s, Date dfl) {
        return dateValue(s, dflFormat(), dfl);
    }

    public static Date dateValue(Object s) {
        return dateValue(s, dflFormat(), new Date());
    }

    /** @deprecated use dateValue instead */
    public static Date simpleDateValue(Object obj, DateFormat df, Date dfl) {
        return dateValue(obj, df, dfl);
    }

    /** @deprecated use dateValue instead */
    public static Date simpleDateValue(Object obj, String format, Date dfl) {
        return dateValue(obj, new SimpleDateFormat(format), dfl);
    }

    /** @deprecated use dateValue instead */
    public static Date simpleDateValue(Object s, DateFormat format) {
        return dateValue(s, format, new Date());
    }

    /** @deprecated use dateValue instead */
    public static Date simpleDateValue(Object s, String format) {
        return dateValue(s, format, new Date());
    }

    /** @deprecated use dateValue instead */
    public static Date simpleDateValue(Object s, Date dfl) {
        return dateValue(s, dflFormat(), dfl);
    }

    /** @deprecated use dateValue instead */
    public static Date simpleDateValue(Object s) {
        return dateValue(s, dflFormat(), new Date());
    }
}
