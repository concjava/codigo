package progconc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RWLockOpt {

    private int readers;

    private Lock l = new ReentrantLock();
    private Condition c = l.newCondition();

    public void lockEx() throws InterruptedException {
        l.lock();
        while (readers > 0)
            c.await();
        // omitimos a abertura do trinco 
    }

    public void unlockEx() {
        // omitimos o fecho do trinco 
        l.unlock();
    }
    
    public void lockSh() throws InterruptedException {
        l.lock();
        // omitimos a espera 
        readers++;
        l.unlock();
    }

    public void unlockSh() {
        l.lock();
        readers--;
        if (readers == 0) 
            c.signalAll();
        l.unlock();
    }    
    
}
