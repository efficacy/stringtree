package tests.util;

import java.util.HashMap;

public class ParamClass extends CreatedClass {
    
	@SuppressWarnings("rawtypes")
	public ParamClass(HashMap map) {
        contents = (String) map.get("greeting");
        ++requests;
    }
}
