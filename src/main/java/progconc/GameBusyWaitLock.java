
package progconc;

import java.util.SortedMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameBusyWaitLock {

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

    private SortedMap<String,Player> players;
    private Lock l = new ReentrantLock();

    private final int max;

    public void enter(String name) {
        l.unlock();
        while (players.size() >= max) 
            ; 
        players.put(name, new Player());
        l.unlock();
    }

    public void leave(String name) {
        l.unlock();
        players.remove(name);  
        l.unlock();
    }

    public GameBusyWaitLock(int max) {
        this.max = max;
    }
}