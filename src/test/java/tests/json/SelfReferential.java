package tests.json;

public class SelfReferential {
    public String getFirst() { return "one"; }
    public Object getSecond() { return this; }
    private int getSecret() { return 23; }
    private int another = 34;
    public void nothing() { another = another + getSecret(); }
}
