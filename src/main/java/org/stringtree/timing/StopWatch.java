package org.stringtree.timing;

public class StopWatch {

    private long start;
    private long stop;
    private long total;

    public StopWatch() {
        reset();
    }

    public void reset() {
        start = System.currentTimeMillis();
        stop = start;
        total = 0;
    }

    public void start() {
        reset();
    }

    public void stop() {
        stop = System.currentTimeMillis();
        total += get();
    }

    public void restart() {
        start = System.currentTimeMillis();
        stop = start;
    }

    public long get() {
        return stop - start;
    }
    
    public long sofar() {
    	return System.currentTimeMillis() - start;
    }

    public long asSeconds() {
        return get() / 1000;
    }

    public long total() {
        return total;
    }

    public long totalSeconds() {
        return total() / 1000;
    }

    public void logSeconds(String text) {
        emit(text + " (" + asSeconds() + "s)");
    }

    public void logMillis(String text) {
        emit(text + " (" + total() + "ms)");
    }
    
    protected void emit(String s) {
        System.out.println(s);
    }
    
    public String toString() {
        return total < 1000 
            ? Long.toString(total) + "ms"
            : Long.toString(totalSeconds()) + "s";
    }
}
