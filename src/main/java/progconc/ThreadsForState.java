package progconc;

public class ThreadsForState {

    public static void main(String[] args) throws Exception {
        final int N = 10;

        var shared = new State(); 

        var t = new Thread[N];
        for(var i=0; i<N; i++) {

            var local = new State(); 

            t[i] = new Thread(()->{
                var v = shared.get(); 
                local.set(v);
                shared.set(v+1); 

                System.out.println(local.get());
            });
            t[i].start();
        }

        for(var i=0; i<N; i++)
            t[i].join();
    }
}
