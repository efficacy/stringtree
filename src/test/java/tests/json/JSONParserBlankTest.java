package tests.json;

public class JSONParserBlankTest extends JSONReaderBlankTest {
    
    JSONParserHelper helper;
    
    public void setUp() {
        helper = new JSONParserHelper();
    }
    
    protected Object read(String text) {
        helper.parse(text);
        return helper.context.get("");
    }
}
