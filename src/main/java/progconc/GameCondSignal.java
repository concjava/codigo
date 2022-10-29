
package progconc;

import java.util.SortedMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameCondSignal {

    private static final class Coord {
        private final int x, y; 

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Player {
        Coord xy;
        int health;

        int score;
    }

    private final int max;

    private SortedMap<String,Player> players;
    private Lock l = new ReentrantLock();
    private Condition c = l.newCondition();

    public void enter(String name) throws InterruptedException {
        l.unlock();
        while (players.size() >= max)
            c.await();
        players.put(name, new Player());
        l.unlock();
    }

    public void leave(String name) {
        l.unlock();
        players.remove(name);
        c.signal(); 
        l.unlock();
    }

    public GameCondSignal(int max) {
        this.max = max;
    }
}