
package progconc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class GameLockOrder {

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

    public void move(String name, int dx, int dy) {
        var player = players.get(name);
        try {
            player.l.lock();
            player.xy = new Coord(player.xy.x + dx, player.xy.y+dy); 
        } finally {
            player.l.unlock();
        }
    }

    public void shoot(String sname, String tname) {
        var splayer = players.get(sname);
        var tplayer = players.get(tname);
        try {
            if (sname.compareTo(tname)<0) {
                splayer.l.lock(); tplayer.l.lock();
            } else {
                tplayer.l.lock(); splayer.l.lock();
            }

            if (tplayer.health > 0) {
                splayer.score += 1;
                tplayer.health -= 1;    
            }
        } finally {
            splayer.l.unlock();
            tplayer.l.unlock();
        }
    }

    private void bonus() {
        try {
            for(var p: players.values()) p.l.lock(); 

            Player best = null;
            for(var p: players.values())
                if (best == null || p.health > best.health)  
                    best = p;
            
            best.score += 1;
        } finally {
            for(var p: players.values()) p.l.unlock();  
        }
    }
}