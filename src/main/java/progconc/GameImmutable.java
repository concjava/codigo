
package progconc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class GameImmutable {

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

    public void move(String name, int dx, int dy) {
        try {
            l.lock();

            var player = players.get(name);
            player.xy = new Coord(player.xy.x + dx, player.xy.y+dy); 
        } finally {
            l.unlock();
        }
    }

    public void shoot(String sname, String tname) {
        try {
            l.lock();

            var splayer = players.get(sname);
            var tplayer = players.get(tname);

            if (tplayer.health > 0) {
                splayer.score += 1;
                tplayer.health -= 1;    
            }
        } finally {
            l.unlock();
        }
    }

    public void draw(DataOutputStream stream) throws IOException {
        List<Coord> coords;

        try {
            l.lock();
            coords = players.values()
                .stream().map(p -> p.xy)
                .collect(Collectors.toList());  
        } finally {
            l.unlock();
        }

        for(var coord: coords) {  
            stream.writeInt(coord.x); 
            stream.writeInt(coord.y);
        }

    }
}