package progconc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RWLock {

    private int readers;
    private boolean writer;

    private Lock l = new ReentrantLock();
    private Condition c = l.newCondition();

    public void lockEx() throws InterruptedException {
        l.lock();
        while (writer || readers>0) 
            c.await();
        writer = true; 
        l.unlock(); 
    }

    public void lockSh() throws InterruptedException {
        l.lock(); 
        while (writer) 
            c.await();
        readers++; 
        l.unlock();
    }

    public void unlockEx() {
        l.lock();
        writer = false; 
        c.signalAll();
        l.unlock();
    }

    public void unlockSh() {
        l.lock();
        readers--; 
        c.signalAll();
        l.unlock();
    }    
    
}
