package server.cardsServer.toolC;

import server.Player;
import shared.Dice;
import shared.Position;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;

import java.util.Random;
@Deprecated
public class ToolC6 extends ToolC {
    public ToolC6() {
        this.setName("toolC6");
        this.setDescription("i2 represents the position in the pool, " +
                "p1 is the possibly next position in window");
    }

    @Override
    public boolean ableAndSettle(Player player, Integer i1) {
        if (player.usedTcAndPlacedDice())
            return false;
        Integer tokens = player.getTokens();
        Integer tokensRequired = player.getGame().getTCtokens(i1);
        if (tokens < tokensRequired)
            return false;
        player.setTokens(tokens - tokensRequired);
        player.getGame().addTCtokens(i1);
        return true;
    }

    @Override
    public boolean use(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettle(player, i1))
            return false;
        if (i2 == null || p1 == null)
            return false;

        Dice dice = game.getPool().get(i2);
        Integer value = dice.value;
        Random rand = new Random();
        dice.value = 1 + rand.nextInt(5);

        if (player.getWindow().setDiceFromPool(player, i2, p1))
            return true;
        dice.value = value;
        return false;
    }
}