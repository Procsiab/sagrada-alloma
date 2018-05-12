package shared.cards.toolC;

import shared.Dice;
import server.Player;
import shared.Position;
import server.abstracts.ToolC;
import server.threads.GameManager;

import java.util.Random;

public class ToolC6 extends ToolC {

    private String name = "1";
    private String description = null;


    public boolean use(Player player, GameManager game, Integer num, Position position){
        Random rand = new Random();
        Integer n = rand.nextInt(6);
        Dice dice = game.pool.get(num);
        dice.value = n;

        return (player.window.setDicePosition(player, dice, position));
    }
}
