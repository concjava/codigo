package progconc;

import java.util.concurrent.TimeUnit;

public class ThreadsForIO {
    public static void main(String[] args) throws Exception {
        var t = new Thread(()->{
            try {
                while (true) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.print(".");
                    System.out.flush();
                }
            } catch (InterruptedException ignored) {}
        });
        t.setDaemon(true);
        t.start(); 

        System.out.println("Press RETURN to quit...");
        System.in.read(); 
    }
}
