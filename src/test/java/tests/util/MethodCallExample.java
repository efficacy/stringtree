package tests.util;

import org.stringtree.finder.StringFinder;
import org.stringtree.finder.StringKeeper;

public class MethodCallExample {
    
    public StringFinder context;
    public int requests = 0;

    public static final int REQUEST = 1;
    public static final int M1 = 2;
    public static final int M2_NO_PARAM = 4;
    public static final int M2_STRING = 8;
    public static final int M2_SF = 16;
    public static final int M2_SK = 32;
    public static final int M3 = 64;

    public void init(StringFinder context) {
        this.context = context;
    }

    public void request() {
        requests += REQUEST;
    }
    
    public void m1(StringFinder context) {
        requests += M1;
    }
    
    public void m2() {
        requests += M2_NO_PARAM;
    }
    
    public void m2(String s) {
        requests += M2_STRING;
    }
    
    public void m2(StringFinder context) {
        requests += M2_SF;
    }
    
    public void m2(StringKeeper context) {
        requests += M2_SK;
    }
    
    public void m3(StringKeeper context) {
        requests += M3;
    }
}
