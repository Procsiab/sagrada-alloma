package shared.cards.toolC;

import shared.Dice;
import server.Player;
import shared.abstracts.ToolC;
import server.threads.GameManager;

import java.util.Random;

public class ToolC7 extends ToolC {

    private String name = "1";
    private String description = null;


    public boolean use(Player player, GameManager game) {
        if (player.privateTurn != 2)
            return false;
        Random rand = new Random();
        for (Dice d :
                game.pool) {
            d.value = rand.nextInt(6);
        }
        return true;
    }
}
