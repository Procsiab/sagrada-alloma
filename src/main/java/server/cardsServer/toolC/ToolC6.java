package server.cardsServer.toolC;

import server.Player;
import shared.Position;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;

public class ToolC6 extends ToolC {
    public ToolC6() {
        this.setName("toolC6");
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

    @Override
    public boolean use(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettle(player,i1 ))
            return false;

        return false;
    }
}