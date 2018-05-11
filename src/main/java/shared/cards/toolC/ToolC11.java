package shared.cards.toolC;

import javafx.geometry.Pos;
import server.MatchManager;
import shared.Dice;
import shared.Player;
import shared.Position;
import shared.abstracts.ToolC;
import shared.GameManager;

import java.util.Random;

public class ToolC11 extends ToolC {

    private String name = "1";
    private String description = null;


    public boolean use(Player player, GameManager game, Integer n, Integer value, Position position) {
        Dice dice = null;
        Random rand = new Random();
        int k = 0;
        while (dice == null) {
            k = rand.nextInt(game.dices.size() - 1);
            dice = game.dices.get(k);
        }
        game.dices.set(k, game.pool.get(n));
        game.pool.set(n, dice);

        return player.window.setDicePosition(player, dice, position);
    }
}