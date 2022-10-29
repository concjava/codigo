package progconc;

public class TwoCounters {
    private long c1 = 0, c2 = 0;

    public void inc() {
        c1 = c1+1;
        c2 = c2+1; 
    }

    public long get() {
        var v2 = c2;
        var v1 = c1; 
        
        return v1-v2;
    }
}
