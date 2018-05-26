package server.cardsServer.toolC;

import shared.Dice;
import server.Player;
import shared.Position;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;

import java.util.Random;

public class ToolC11 extends ToolC {

    public ToolC11() {
        this.name = "toolC11";
    }

    @Override
    public boolean ableAndSettle(Player player) {
        Integer tokens = player.tokens;
        if (tokens < tokensRequired)
            return false;
        player.tokens = tokens - tokensRequired;
        tokensRequired = 2;
        player.hasUsedTc = true;
        return true;
    }


    public boolean use(GameManager game, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettle(player))
            return false;  Dice dice = null;
        Random rand = new Random();
        int k = 0;
        while (dice == null) {
            k = rand.nextInt(game.dices.size() - 1);
            dice = game.dices.get(k);
        }
        game.dices.set(k, game.pool.get(i2));
        game.pool.set(i2, dice);

        return player.window.setDicePositionFromPool(player,i2, p1);
    }
}