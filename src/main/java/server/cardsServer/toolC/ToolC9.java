package server.cardsServer.toolC;

import shared.Dice;
import server.Player;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;
import shared.Position;

public class ToolC9 extends ToolC {

    public ToolC9() {
        this.setName("toolC9");
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
        Dice dice = game.pool.get(i2);

        if (!player.window.checkEdgePosTurn(player, p1))
            return false;
        if (!player.window.checkPlaceRequirements(dice, p1))
            return false;

        //check if in the surroundings there is not an other dice

        player.overlay.getDicePositions()[p1.getRow()][p1.getColumn()] = dice;
        game.pool.set(i2, null);
        return true;
    }
}