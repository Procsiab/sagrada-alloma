package server.cardsServer.toolC;

import server.Player;
import shared.Dice;
import shared.Position;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;

public class ToolC10 extends ToolC {

    public ToolC10() {
        this.setName("toolC10");
        this.setDescription("i2 is the index in the pool, p1 is the presumably final position");
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


    public boolean use(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettle(player, i1))
            return false;
        if (i2 == null || p1 == null)
            return false;
        Dice dice = game.getPool().get(i2);
        if (dice == null)
            return false;

        Integer value = game.getPool().get(i2).value;
        if (value == 1)
            game.getPool().get(i2).value = 6;
        else if (value == 2)
            game.getPool().get(i2).value = 5;
        else if (value == 3)
            game.getPool().get(i2).value = 4;
        else if (value == 4)
            game.getPool().get(i2).value = 3;
        else if (value == 5)
            game.getPool().get(i2).value = 2;
        else
            game.getPool().get(i2).value = 1;

        return player.getWindow().setDiceFromPool(player, i2, p1);
    }
}