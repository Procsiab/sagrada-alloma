package server.cardsServer.toolC;

import server.threads.GameManager;
import server.Player;
import shared.PositionR;
import server.abstractsServer.ToolC;
import shared.Position;

public class ToolC3 extends ToolC {
    public ToolC3() {
        this.setName("toolC3");
        this.setDescription("p1 is the original position, p2 is the allegedly future position");
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

        if (!ableAndSettle(player, i1))
            return false;
        if (p1 == null || p2 == null)
            return false;
        return player.getWindow().moveDiceNoShade(player, p1, p2);
    }
}