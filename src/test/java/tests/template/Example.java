package tests.template;

import org.stringtree.finder.StringFinder;
import org.stringtree.finder.StringKeeper;

public class Example {
    
    public String convert(String s) {
        return s.toUpperCase();
    }
    
    public String getItalic(String s) {
        return "/" + s + "/";
    }
    
    public String bold(String s) {
        return "*" + s + "*";
    }
    
    public String bold(FullName name) {
        return "*" + name.forename() + "*";
    }
    
    public Object underline(Object obj) {
        return "_" + obj + "_";
    }

    public String fbold(StringFinder finder, String s) {
        return "f*" + s + "*";
    }
    
    public Object funderline(StringFinder finder, Object obj) {
        return "f_" + obj + "_";
    }
    
    public Object fname(StringFinder finder, FullName name) {
        return "f~" + name.forename() + "~";
    }

    public String kbold(StringKeeper keeper, String s) {
        return "k*" + s + "*";
    }
    
    public Object kunderline(StringKeeper keeper, Object obj) {
        return "k_" + obj + "_";
    }
    
    public Object kname(StringKeeper keeper, FullName name) {
        return "k~" + name.forename() + "~";
    }
    
    public String dibble(String text, Integer reps) {
    	StringBuffer ret = new StringBuffer(text.length() * reps);
    	for (int i = 0; i < reps; ++i) {
    		ret.append(text);
    	}
    	return ret.toString();
    }
    
    public boolean test(String name) {
    	return name.equalsIgnoreCase("Frank");
    }
}
