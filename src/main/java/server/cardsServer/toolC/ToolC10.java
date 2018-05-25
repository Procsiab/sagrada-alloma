package server.cardsServer.toolC;

import server.Player;
import shared.Position;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;

public class ToolC10 extends ToolC {

    public ToolC10() {
        this.name = "toolC10";
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
            return false;Integer value = game.pool.get(i2).value;
        if(value == 1)
            value = 6;
        else if(value == 2)
            value = 5;
        else if(value == 3)
            value = 4;
        else if(value == 4)
            value = 3;
        else if(value ==5)
            value = 2;
        else
            value = 1;
        return true;
    }
}
