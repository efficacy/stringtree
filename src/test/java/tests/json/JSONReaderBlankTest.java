package tests.json;

public class JSONReaderBlankTest extends JSONTestCase {

    public void testEmpty() {
    	assertNull(read(""));
    }

    public void testJustWhiteSpace() {
        assertNull(read(" "));
        assertNull(read("  "));
        assertNull(read("\t  "));
        assertNull(read("  \t"));
        assertNull(read("\n  "));
        assertNull(read("  \n"));
        assertNull(read("\f  "));
        assertNull(read("  \f"));
    }

    public void testLotsOfMixedWhiteSpace() {
    	assertNull(read(" \n \t \f \n \r\n \t\t \n\r"));
        assertInteger(123, read(" \n \t \f \n \r\n \t\t \n\r123"));
    }

}
