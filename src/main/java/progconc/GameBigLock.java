
package progconc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.SortedMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameBigLock {

    private static class Player {
        int x,y;
        int health;

        int score;
    }

    private SortedMap<String,Player> players;
    private Lock l = new ReentrantLock();

    public void move(String name, int dx, int dy) {
        try {
            l.lock(); 

            var player = players.get(name);

            player.x += dx;
            player.y += dy;    
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
        try {
            l.lock();

            for(var player: players.values()) {
                stream.writeInt(player.x);
                stream.writeInt(player.y); 
            }
        } finally {
            l.unlock();
        }
    }
}