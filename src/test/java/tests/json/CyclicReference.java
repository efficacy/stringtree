package tests.json;

public class CyclicReference {
    private Object other;
    public Object getOther() { return other ; }
    public void setOther(Object other) { this.other = other; }
}
