
package progconc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.SortedMap;

public class Game {

    private static class Player {
        int x,y;
        int health;

        int score;
    }

    private SortedMap<String,Player> players;

    public void move(String name, int dx, int dy) {
        var player = players.get(name);

        player.x += dx;
        player.y += dy;
    }

    public void shoot(String sname, String tname) {
        var splayer = players.get(sname);
        var tplayer = players.get(tname);

        if (tplayer.health > 0) {
            splayer.score += 1;
            tplayer.health -= 1;    
        }
    }

    public void draw(DataOutputStream stream) throws IOException {
        for(var player: players.values()) {
            stream.writeInt(player.x);
            stream.writeInt(player.y);
        }
    }
}