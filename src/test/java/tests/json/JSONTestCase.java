package tests.json;

import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONValidatingReader;
import org.stringtree.json.JSONValidatingWriter;
import org.stringtree.json.JSONValidator;
import org.stringtree.json.JSONWriter;

import junit.framework.TestCase;

public class JSONTestCase extends TestCase {
    JSONReader reader;
    JSONWriter writer;
    JSONValidator validator;
    TestCaseErrorListener listener;
    Object obj;
    
    public void setUp() {
        reader = new JSONValidatingReader();
        writer = new JSONValidatingWriter();
        listener = new TestCaseErrorListener();
        validator = new JSONValidator(listener);
    }
    
    protected Object read(String text) {
        obj = reader.read(text);
        assertTrue(obj != JSONValidatingReader.INVALID);
        return obj;
    }
    
    protected boolean validate(String text) {
        boolean ret = validator.validate(text);
        if (!ret) {
            System.err.println("validate failed col=" + listener.column + " message='" + listener.message + "'");
            throw new RuntimeException("huh");
        }
        return ret;
    }
    
    protected boolean validateOK(String text) {
        return validator.validate(text);
    }
    
    protected boolean validate(String text, int column, String message) {
        boolean valid = validator.validate(text); 
        boolean ret = !valid && listener.column==column && listener.message.equals(message);
        if (!ret) {
            System.err.println("validate (" + text + ") expecting valid=false,col=" + column +",message='" + message + "' actual valid=" + valid + ",col=" + listener.column + ",message='" + listener.message + "'");
        }
        return ret;
    }
    
    protected String write(Object obj) {
        return writer.write(obj);
    }
    
    protected String write(boolean bool) {
        return writer.write(bool);
    }

    protected String write(long n) {
        return writer.write(n);
    }

    protected Object write(double d) {
        return writer.write(d);
    }

    protected String write(char c) {
        return writer.write(c);
    }

    protected void assertInteger(int desired, Object value) {
        assertTrue(value instanceof Number);
        assertEquals(desired, ((Number) value).intValue());
    }

    protected void assertDouble(double desired, Object value, double tolerance) {
        assertTrue(value instanceof Number);
        assertEquals(desired, ((Number) value).doubleValue(), tolerance);
    }

    protected void assertDouble(double desired, Object value) {
        assertDouble(desired, value, 0.0001);
    }

    protected void assertValid(String text) {
        assertTrue(validateOK(text));
    }

    protected void assertInvalid(String text, int col, String message) {
        assertTrue(validate(text, col, message));
    }
}
