package progconc;

public class WriteRace {
    private static final int N=10, M=1000000;

    public static void main(String[] args) throws Exception {
        var c = new Counter();

        Thread[] t = new Thread[N];
        for(var i=0; i<N; i++) {
            t[i] = new Thread(()->{
                for(var j=0; j<M; j++)
                    c.inc();
            });
        }
        
        for(var i=0; i<N; i++)
            t[i].start();
        for(var i=0; i<N; i++)
            t[i].join();

        System.out.println(c.get()+" vs. "+(N*M)); 
    }
}
