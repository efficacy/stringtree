package tests.fetcher;

public class SimpleBean {
    
    public String getAa() {
        return "hello from aa";
    }

    public String bb() {
        return "hello from bb";
    }

    public String cc = "hello from cc";
    
    public boolean isBoolD() {
        return true;
    }
    
    public boolean getBoolD() {
        return false;
    }
    
    public Boolean isBoolE() {
        return Boolean.TRUE;
    }
    
    public Boolean getBoolE() {
        return Boolean.FALSE;
    }
    
    public String stone = "direct";
    public String getStone() {
        return "method";
    }
    
    public boolean blood = true;
    public boolean isBlood() {
        return false;
    }
    
    public String onestring(String a) {
    	return "single[" + a + "]";
    }
    
    public String two(String a, String b) {
    	return "double[" + a + "][" + b + "]";
    }
    
    public String two(String a, Integer b) {
    	return "si[" + a + "][" + b + "]";
    }
    
    public String two(Integer a, Integer b) {
    	return "ii[" + a + "][" + b + "]";
    }
    
    public String getNoArgs() {
    	return "property";
    }
    
    public String noArgs() {
    	return "method";
    }
    
    public boolean test(String name) {
    	return name.equalsIgnoreCase("Frank");
    }
}
