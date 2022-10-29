package progconc;

public class BusyWait {
    private static final int M=1000000;

    public static void main(String[] args) throws Exception {
        var c = new Counter();

        var t = new Thread(()->{
            for(var j=0; j<M; j++)
                c.inc();
        });
        t.start();

        while (!(c.get() >= M/2)) 
            ; 

        System.out.println("metade da tarefa");

        t.join();
    }
}
