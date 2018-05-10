package shared.cards.toolC;

import shared.Dice;
import shared.Player;
import shared.abstracts.ToolC;
import shared.GameManager;
import shared.Position;

public class ToolC9 extends ToolC {

    private String name = "1";
    private String description = null;


    public boolean use(Player player, GameManager game, Integer n, Position position) {
        Dice dice = game.pool.get(n);
        if (!player.window.checkEdgePosTurn(player, position))
            return false;
        if (!player.window.checkPlaceRequirements(dice, position))
            return false;
        player.overlay.dicePositions[position.getRow()][position.getColumn()] = dice;
        game.pool.set(n,null);
        return true;
    }
}