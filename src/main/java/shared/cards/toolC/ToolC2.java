package shared.cards.toolC;

import shared.Dice;
import shared.Player;
import shared.abstracts.ToolC;
import shared.Position;
import shared.GameManager;

public class ToolC2 extends ToolC {

    private String name = "2";
    private String description = null;

    public boolean use(Player player, Position pos1, Position pos2) {
        Dice[][] dicePositions = player.overlay.dicePositions;
        Dice dice = dicePositions[pos1.getRow()][pos2.getColumn()];
        if (!player.window.checkAdjDicesFull(player.overlay, pos2, dice)) {
            return false;
        }
        if (!player.window.checkEdgePosTurn(player, pos2))
            return false;
        if (!player.window.checkPlaceValueRequirements(dice, pos2))
            return false;
        dicePositions[pos1.getRow()][pos1.getColumn()] = null;
        dicePositions[pos2.getRow()][pos2.getColumn()] = dice;
        return true;
    }
}