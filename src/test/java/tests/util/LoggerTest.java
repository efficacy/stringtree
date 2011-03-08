package tests.util;

import java.io.StringWriter;

import junit.framework.TestCase;

import org.stringtree.util.logging.CategoryLogger;

public class LoggerTest extends TestCase {
    
    protected StringWriter out;
    protected CategoryLogger logger;
    protected String nl = System.getProperty("line.separator");

    public LoggerTest(String name) {
        super(name);
    }

    public void setUp() {
        out = new StringWriter();
        logger = new CategoryLogger(out);

        logger.addCategory("YES", "ALL");
        logger.addCategory("NO", "ALL");
        logger.preventLogging("NO");
    }

    public void testIsLoggable() {
        assertTrue(logger.isLoggable(CategoryLogger.ALL));
        assertTrue(logger.isLoggable("ALL"));
        assertTrue(logger.isLoggable("TEST"));

        logger.preventLogging(CategoryLogger.ALL);
        assertTrue(!logger.isLoggable("ALL"));
        assertTrue(!logger.isLoggable("TEST"));

        logger.allowLogging(CategoryLogger.ALL);
        assertTrue(logger.isLoggable("ALL"));
        assertTrue(logger.isLoggable("TEST"));

        logger.preventLogging("TEST");
        assertTrue(logger.isLoggable("ALL"));
        assertTrue(!logger.isLoggable("TEST"));
    }

    public void testAll() {
        logger.log("ALL", "hello");
        assertEquals("(ALL) hello" + nl, out.toString());
    }

    public void testYes() {
        logger.log("YES", "hello");
        assertEquals("(YES) hello" + nl, out.toString());
    }

    public void testNo() {
        logger.log("NO", "hello");
        assertEquals("", out.toString());
    }

    public void testAllIndent() {
        logger.log("ALL", "hello", 4);
        assertEquals("(ALL)     hello" + nl, out.toString());
    }

    public void testYesIndent() {
        logger.log("YES", "hello", 9);
        assertEquals("(YES)          hello" + nl, out.toString());
    }

    public void testNoIndent() {
        logger.log("NO", "hello", 3);
        assertEquals("", out.toString());
    }
}