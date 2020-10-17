package tests.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

import org.stringtree.util.DateUtils;
import org.stringtree.util.IntegerNumberUtils;
import org.stringtree.util.LongNumberUtils;
import org.stringtree.util.StringUtils;

public class UtilTest extends TestCase {

    public void testStringValue() {
        assertEquals("null -> null", null, StringUtils.stringValue(null));
        assertEquals("blank -> blank", "", StringUtils.stringValue(""));
        assertEquals("whatever -> whatever", "whatever", StringUtils
                .stringValue("whatever"));
        assertEquals("numeric -> numeric", "1", StringUtils
                .stringValue(Integer.valueOf(1)));
    }

    public void testSimpleIntValue() {
        assertEquals("null -> 0", 0, IntegerNumberUtils.intValue(null));
        assertEquals("null(12) -> 12", 12, IntegerNumberUtils
                .intValue(null, 12));
        assertEquals("'' -> 0", 0, IntegerNumberUtils.intValue(""));
        assertEquals("''(12) -> 12", 12, IntegerNumberUtils.intValue("", 12));
        assertEquals("'whatever' -> 0", 0, IntegerNumberUtils
                .intValue("whatever"));
        assertEquals("'whetever'(12) -> 12", 12, IntegerNumberUtils.intValue(
                "whatever", 12));
        assertEquals("'3' -> 3", 3, IntegerNumberUtils.intValue("3"));
        assertEquals("'3'(12) -> 3", 3, IntegerNumberUtils.intValue("3", 12));
        assertEquals("'3.2' -> 3", 3, IntegerNumberUtils.intValue("3"));
        assertEquals("'3.2'(12) -> 3", 3, IntegerNumberUtils.intValue("3", 12));

        assertEquals("3 -> 3", 3, IntegerNumberUtils.intValue(Integer.valueOf(3)));
        assertEquals("3(12) -> 3", 3, IntegerNumberUtils.intValue(
                Integer.valueOf(3), 12));
        assertEquals("3.2 -> 3", 3, IntegerNumberUtils.intValue(Float.valueOf(3.2f)));
        assertEquals("3.2(12) -> 3", 3, IntegerNumberUtils.intValue(Float.valueOf(3.2f), 12));
        assertEquals("3.99 -> 3", 3, IntegerNumberUtils
                .intValue(Float.valueOf(3.99f)));
        assertEquals("3.99(12) -> 3", 3, IntegerNumberUtils.intValue(Float.valueOf(3.99f), 12));

        assertEquals("3000000 -> 3000000", 3000000, IntegerNumberUtils
                .intValue(Float.valueOf(3000000)));
        assertEquals("3000000(12) -> 3000000", 3000000, IntegerNumberUtils
                .intValue(Float.valueOf(3000000), 12));
    }

    public void testSimpleLongValue() {
        assertEquals("null -> 0", 0, LongNumberUtils.longValue(null));
        assertEquals("null(12) -> 12", 12, LongNumberUtils.longValue(null, 12));
        assertEquals("'' -> 0", 0, LongNumberUtils.longValue(""));
        assertEquals("''(12) -> 12", 12, LongNumberUtils.longValue("", 12));
        assertEquals("'whatever' -> 0", 0, LongNumberUtils
                .longValue("whatever"));
        assertEquals("'whetever'(12) -> 12", 12, LongNumberUtils.longValue(
                "whatever", 12));
        assertEquals("'3' -> 3", 3, LongNumberUtils.longValue("3"));
        assertEquals("'3'(12) -> 3", 3, LongNumberUtils.longValue("3", 12));
        assertEquals("'3.2' -> 3", 3, LongNumberUtils.longValue("3"));
        assertEquals("'3.2'(12) -> 3", 3, LongNumberUtils.longValue("3", 12));

        assertEquals("3 -> 3", 3, LongNumberUtils.longValue(Long.valueOf(3)));
        assertEquals("3(12) -> 3", 3, LongNumberUtils
                .longValue(Long.valueOf(3), 12));
        assertEquals("3.2 -> 3", 3, LongNumberUtils.longValue(Float.valueOf(3.2f)));
        assertEquals("3.2(12) -> 3", 3, LongNumberUtils.longValue(
                Float.valueOf(3.2f), 12));
        assertEquals("3.99 -> 3", 3, LongNumberUtils.longValue(Float.valueOf(3.99f)));
        assertEquals("3.99(12) -> 3", 3, LongNumberUtils.longValue(Float.valueOf(3.99f), 12));

        assertEquals("3000000 -> 3000000", 3000000, LongNumberUtils
                .longValue(Float.valueOf(3000000)));
        assertEquals("3000000(12) -> 3000000", 3000000, LongNumberUtils
                .longValue(Float.valueOf(3000000), 12));
    }

    public void testSimpleDateValue() {
        Calendar nowc = new GregorianCalendar(2001, 8 - 1, 13);
        Date now = nowc.getTime();

        Calendar thenc = new GregorianCalendar(1997, 4 - 1, 17);
        Date then = thenc.getTime();

        java.text.DateFormat fmt = new java.text.SimpleDateFormat(
                "dd-MMMM-yyyy");

        assertTrue("two references not equal", !now.equals(then));

        assertEquals("builtin DateFormat", now, DateUtils
                .dateValue("13/08/2001"));

        assertEquals("builtin with dfl - ok", now, DateUtils.dateValue(
                "13/08/2001", then));
        assertEquals("builtin with dfl - null", then, DateUtils.dateValue(null,
                then));
        assertEquals("builtin with dfl - empty", then, DateUtils.dateValue("",
                then));
        assertEquals("builtin with dfl - invalid", then, DateUtils.dateValue(
                "hello", then));

        assertEquals("builtin with dfl - Date", now, DateUtils.dateValue(now,
                then));
        assertEquals("builtin with dfl - Calendar", now, DateUtils.dateValue(
                nowc, then));

        assertEquals("specified DateFormat", now, DateUtils.dateValue(
                "13-Aug-2001", fmt));

        assertEquals("specified with dfl - ok", now, DateUtils.dateValue(
                "13-Aug-2001", fmt, then));
        assertEquals("specified with dfl - null", then, DateUtils.dateValue(
                null, fmt, then));
        assertEquals("specified with dfl - empty", then, DateUtils.dateValue(
                "", fmt, then));
        assertEquals("specified with dfl - invalid", then, DateUtils.dateValue(
                "hello", fmt, then));
    }

    public void testIsBlank() {
        assertTrue("null -> true", StringUtils.isBlank(null));
        assertTrue("empty -> true", StringUtils.isBlank(""));
        assertTrue("' ' -> true", StringUtils.isBlank(" "));
        assertTrue("'    ' -> true", StringUtils.isBlank("    "));

        assertTrue("'t' -> false", !StringUtils.isBlank("t"));
        assertTrue("'text' -> false", !StringUtils.isBlank("text"));
        assertTrue("'  text' -> false", !StringUtils.isBlank("  text"));
        assertTrue("'text  ' -> false", !StringUtils.isBlank("text  "));
    }

    public void testNullToEmpty() {
        assertEquals("null -> ''", "", StringUtils.nullToEmpty(null));
        assertEquals("string -> 'xx'", "xx", StringUtils.nullToEmpty("xx"));
        assertEquals("non-string -> '33'", "33", StringUtils
                .nullToEmpty(Integer.valueOf(33)));
    }
}
