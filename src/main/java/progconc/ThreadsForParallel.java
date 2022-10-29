package progconc;

public class ThreadsForParallel {
    private static int subtask(int base, int len) {
        int c = len;
        for(var i=base; i<base+len; i++) {
            for(var j=2; j<i; j++)
                if (i%j == 0) {
                    c = c-1;
                    break;
                }
        }
        return c;
    }

    public static void main(String[] args) throws Exception {
        final int N = 10, M = 1000;
        final var len = M/N;

        var t = new Thread[N];
        for(var i=0; i<N; i++) {
            final var slice = i*len;
            t[i] = new Thread(()->{
                subtask(slice, len);
            });
            t[i].start();
        }

        for(var i=0; i<N; i++)
            t[i].join();
    }
}
