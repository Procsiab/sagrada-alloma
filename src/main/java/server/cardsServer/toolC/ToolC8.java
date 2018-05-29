package server.cardsServer.toolC;

import server.Player;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;
import shared.Position;

public class ToolC8 extends ToolC {

    public ToolC8() {
        this.setName("toolC8");
    }

    @Override
    public boolean ableAndSettle(Player player) {
        if(player.hasUsedTc)
            return false;
        Integer tokens = player.tokens;
        if (tokens < this.getTokensRequired())
            return false;
        player.tokens = tokens - this.getTokensRequired();
        this.setTokensRequired(2);
        player.hasUsedTc = true;
        return true;
    }

    @Override
    public boolean use(GameManager game, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettle(player))
            return false;

        if (player.privateTurn != 1)
            return false;

        if (player.window.setDicePositionFromPool(player,0, p1)) {
            game.jump.add(player.uUID);
            return true;
        }
        return false;
    }
}
