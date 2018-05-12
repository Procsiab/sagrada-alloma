package shared.cards.toolC;

import server.Player;
import server.abstracts.ToolC;
import server.threads.GameManager;
import shared.Position;

public class ToolC8 extends ToolC {

    private String name = "1";
    private String description = null;


    public boolean use(Player player, GameManager game, Integer n, Position position) {
        if (player.privateTurn != 1)
            return false;

        if (player.window.setDicePosition(player, game.pool.get(n), position)) {
            game.jump.add(player.uUID);
            return true;
        }
        return false;
    }
}
