package tests.util;

import java.util.HashMap;

public class BothClass extends CreatedClass {
    
    public BothClass() {
        contents = "Hello";
        ++requests;
    }

    @SuppressWarnings("unchecked")
	public BothClass(HashMap map) {
        contents = (String) map.get("greeting");
        ++requests;
    }
}
