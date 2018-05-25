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

        Dice[][] dicePositions = player.overlay.getDicePositions();
        Dice dice = dicePositions[p1.getRow()][p2.getColumn()];
        if (!player.overlay.busy(p1))
            return false;
        if (player.overlay.busy(p2))
            return false;
        if (!player.window.checkAdjDicesFull(player.overlay, p2, dice))
            return false;
        if (!player.window.checkEdgePosTurn(player, p2))
            return false;
        if (!player.window.checkPlaceColorRequirements(dice, p2))
            return false;

        player.overlay.setDicePosition(null, p1);
        player.overlay.setDicePosition(dice, p2);
        return true;
    }
}