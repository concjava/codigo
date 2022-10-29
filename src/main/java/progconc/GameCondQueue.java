
package progconc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.SortedMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameCondQueue {

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

    private final int max = 10;

    private SortedMap<String,Player> players;
    private Lock l = new ReentrantLock();
    private Queue<Condition> q = new LinkedList<>(); 

    private int nextTicket, nextTurn = max;

    public void enter(String name) throws InterruptedException {
        l.unlock();
        var ticket = nextTicket++;
        if (ticket>nextTurn) {
            var c = l.newCondition(); 
            q.add(c);
            do
                c.await();
            while (ticket>nextTurn);
        }
        players.put(name, new Player());
        l.unlock();
    }

    public void leave(String name) {
        l.unlock();
        players.remove(name);
        nextTurn++;
        var c = q.remove();
        if (c != null)
            c.signal(); 
        l.unlock();
    }
}