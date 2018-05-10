package shared.cards.toolC;

import shared.*;
import shared.abstracts.ToolC;

public class ToolC12 extends ToolC {

    private String name = "1";
    private String description = null;

    //todo: descrizione imprecisa

    public boolean use(Player player, GameManager game, Position position1, Position position2, PositionR positionR) {
        return false;
    }

    public boolean use(Player player, GameManager game, Position position1a, Position position2a, Position position1b, Position position2b, PositionR positionR) {
        char color = game.roundTrack.dices.get(positionR.column).get(positionR.height).color;
        if (color != player.overlay.dicePositions[position1a.getRow()][position1a.getColumn()].color
                || color != player.overlay.dicePositions[position2a.getRow()][position2a.getColumn()].color)
            return false;

        //check the restrictions and move dices
        return true;
    }
}