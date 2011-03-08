package tests.json;

public class JSONReaderDirectTest extends JSONTestCase {

    public void testAll() {
        assertEquals(Boolean.TRUE, read("true"));
        assertEquals(Boolean.FALSE, read("false"));
        assertEquals(null,read("null"));
    }
}
