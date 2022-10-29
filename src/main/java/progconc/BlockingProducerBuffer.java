
package progconc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingProducerBuffer<T> {
    private Queue<T> q = new LinkedList<>();
    private Lock l = new ReentrantLock();
    private Condition c = l.newCondition();
    private final int MAX = 10;

    public T get() {
        try { 
            l.lock();
            c.signal(); 
            return q.remove(); 
        } finally {
            l.unlock();
        }
    }

    public void put(T s) throws InterruptedException {
        try { 
            l.lock();
            while (q.size() >= MAX) 
                c.await(); 
            q.add(s); 
        } finally {
            l.unlock();
        }
    }
}
