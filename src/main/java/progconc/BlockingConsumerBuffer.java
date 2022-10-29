
package progconc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingConsumerBuffer<T> {
    private Queue<T> q = new LinkedList<>();
    private Lock l = new ReentrantLock();
    private Condition c = l.newCondition();

    public T get() throws InterruptedException {
        try { 
            l.lock();
            while (q.isEmpty()) 
                c.await(); 
            return q.remove();  
        } finally {
            l.unlock();
        }
    }

    public void put(T s) {
        try { 
            l.lock();
            q.add(s); 
            c.signal(); 
        } finally {
            l.unlock();
        }
    }
}
