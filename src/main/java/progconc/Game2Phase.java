
package progconc;

import java.util.SortedMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Game2Phase {

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

        private Lock l = new ReentrantLock();
    }

    private SortedMap<String,Player> players;

    private void bonus() {
        Player best = null;
        for(var p: players.values()) {  
            p.l.lock();
            if (best == null || p.health > best.health)
                best = p;
        }

        for(var p: players.values()) {   
            if (p != best)
                p.l.unlock();
        }
        best.score += 1; 
        best.l.unlock();
    }
}