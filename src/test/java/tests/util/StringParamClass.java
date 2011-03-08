package tests.util;

public class StringParamClass extends CreatedClass {
    
    public StringParamClass(String s) {
        contents = s;
        ++requests;
    }
}
