package progconc;

public class Counter {
    private long c = 0;

    public void inc() {
        c = c+1; 
    }

    public long get() {
        return c;
    }
}
