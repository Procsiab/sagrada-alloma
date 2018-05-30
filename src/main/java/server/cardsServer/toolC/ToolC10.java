package server.cardsServer.toolC;

import server.Player;
import shared.Position;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;

public class ToolC10 extends ToolC {

    public ToolC10() {
        this.setName("toolC10");
    }

    @Override
    public boolean ableAndSettle(Player player, Integer i1) {
        if (player.usedTc())
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

        if (!ableAndSettle(player,i1 ))
            return false;
        Integer value = game.getPool().get(i2).value;
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
