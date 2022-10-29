
package progconc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer<T> {
    private Queue<T> q = new LinkedList<>();
    private Lock l = new ReentrantLock();
    private Condition notEmpty = l.newCondition();
    private Condition notFull = l.newCondition();
    private final int MAX = 10;

    public T get() throws InterruptedException {
        try { 
            l.lock();
            while (q.isEmpty()) 
                notEmpty.await(); 
            notFull.signal(); 
            return q.remove(); 
        } finally {
            l.unlock();
        }
    }

    public void put(T s) throws InterruptedException {
        try { 
            l.lock();
            while (q.size() >= MAX) 
                notFull.await(); 
            q.add(s);   
            notEmpty.signal(); 
        } finally {
            l.unlock();
        }
    }
}
