package tests.db;

public class RecordingFetcher extends RecordingProxy {
    public Object getObject(String name) {
        return "%" + name;
    }
}
