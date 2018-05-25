package server.cardsServer.toolC;

import server.Player;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;
import shared.Position;

public class ToolC8 extends ToolC {

    public ToolC8() {
        this.name = "toolC8";
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
            return false;

        if (player.privateTurn != 1)
            return false;

        if (player.window.setDicePositionFromPool(player,0, game.pool.get(i2), p1)) {
            game.jump.add(player.uUID);
            return true;
        }
        return false;
    }
}
