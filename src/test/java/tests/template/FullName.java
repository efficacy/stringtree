package tests.template;

public class FullName {

    private String fn;
    private String sn;

    public FullName(String fn, String sn) {
        this.fn = fn;
        this.sn = sn;
    }

    public String forename() {
        return fn;
    }

    public String surname() {
        return sn;
    }
    
    public String toString() {
        return fn + " " + sn;
    }
}
