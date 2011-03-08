package tests.util;

import java.util.HashMap;

public class ParamClass extends CreatedClass {
    
    @SuppressWarnings("unchecked")
	public ParamClass(HashMap map) {
        contents = (String) map.get("greeting");
        ++requests;
    }
}
