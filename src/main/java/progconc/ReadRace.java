package progconc;

public class ReadRace {
    private static final int M=1000000;

    public static void main(String[] args) throws Exception {
        var c = new TwoCounters();

        var t = new Thread(()->{
            for(var j=0; j<M; j++)
                c.inc();
        });
        t.start();

        var i=0;
        for(var j=0; j<M; j++) {
            if (c.get()>=0) 
                i++;
        }

        t.join();
        
        System.out.println(i+" vs. "+M);
    }
}
