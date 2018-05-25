package server.cardsServer.toolC;

import server.threads.GameManager;
import shared.Dice;
import server.Player;
import shared.PositionR;
import server.abstractsServer.ToolC;
import shared.Position;

public class ToolC4 extends ToolC {

    public ToolC4() {
        this.name = "toolC4";
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
        Dice dice1 = dicePositions[p1.getRow()][p2.getColumn()];
        if (!player.overlay.busy(p1))
            return false;
        if (player.overlay.busy(p2))
            return false;
        if (!player.window.checkDicePosition(player, dice1, p2))
            return false;
        player.overlay.setDicePosition(null, p1);
        player.overlay.setDicePosition(dice1, p2);

        Dice dice2 = dicePositions[p1.getRow()][p2.getColumn()];
        if (!player.overlay.busy(p3)) {
            player.overlay.setDicePosition(dice1, p1);
            player.overlay.setDicePosition(null, p2);
            return false;
        }
        if (player.overlay.busy(p4)) {
            player.overlay.setDicePosition(dice1, p1);
            player.overlay.setDicePosition(null, p2);
            return false;
        }
        if (!player.window.checkDicePosition(player, dice2, p3)) {
            player.overlay.setDicePosition(dice1, p1);
            player.overlay.setDicePosition(null, p2);
            return false;
        }
        player.overlay.setDicePosition(null, p3);
        player.overlay.setDicePosition(dice1, p4);
        return true;
    }
}