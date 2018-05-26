package server.cardsServer.toolC;

import server.threads.GameManager;
import shared.Dice;
import server.Player;
import shared.PositionR;
import server.abstractsServer.ToolC;
import shared.Position;

public class ToolC3 extends ToolC {
    public ToolC3() {
        this.name = "toolC3";
        this.description = "p1 is the original position, p2 is the allegedly future position";
    }

    @Override
    public boolean ableAndSettle(Player player) {
        if (player.hasUsedTc)
            return false;
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
            return false;
        if (p1 == null || p2 == null)
            return false;
        return player.window.moveDicePositionNoShade(player, p1, p2);
    }
}