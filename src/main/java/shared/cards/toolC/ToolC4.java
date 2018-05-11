package shared.cards.toolC;

import shared.Dice;
import shared.Player;
import shared.abstracts.ToolC;
import shared.Position;
import shared.GameManager;

public class ToolC4 extends ToolC {

    private String name = "1";
    private String description = null;

    //todo: imprecisione nel testo

    public boolean use(Player player, Position pos1a, Position pos2a, Position pos1b, Position pos2b ) {
        Dice[][] dicePositions = player.overlay.dicePositions;
        Dice dice1 = dicePositions[pos1a.getRow()][pos1a.getColumn()];
        Dice dice2 = dicePositions[pos2a.getRow()][pos2a.getColumn()];

        return false;
    }
}
