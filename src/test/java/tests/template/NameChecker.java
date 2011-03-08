package tests.template;

public class NameChecker {
    public boolean isDeveloper(FullName name) {
        return "Frank".equals(name.forename());
    }
}
