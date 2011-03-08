package tests.spec;

public class DummyWithConstructor extends DummyClass {
    
    public String value = "unset";

    public DummyWithConstructor(String value) {
        this.value = value;
    }

    public boolean equals(Object other) {
        return other instanceof DummyWithConstructor
                && ((DummyWithConstructor) other).value.equals(value);
    }
    
    @Override
    public int hashCode() {
        return "$DummyWIthConstructor$".hashCode() + value.hashCode();
    }

    public String toString() {
        return "DummyWithConstructor:" + value;
    }
}
