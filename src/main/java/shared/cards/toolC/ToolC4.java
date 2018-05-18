package shared.cards.toolC;

import shared.Dice;
import server.Player;
import shared.abstracts.ToolC;
import shared.Position;

public class ToolC4 extends ToolC {

    private String name = "1";
    private String description = null;

    public boolean use(Player player, Position pos1a, Position pos2a, Position pos1b, Position pos2b ) {
        Dice[][] dicePositions = player.overlay.getDicePositions();
        Dice dice1 = dicePositions[pos1a.getRow()][pos1a.getColumn()];
        Dice dice2 = dicePositions[pos2a.getRow()][pos2a.getColumn()];

        if(player.window.setDicePosition(player,dice1,pos2a)&& player.window.setDicePosition(player,dice2,pos2b)){
            dicePositions[pos1a.getRow()][pos1a.getColumn()] = null;
            dicePositions[pos2a.getRow()][pos2a.getColumn()] = null;
            player.game.updateView(player.uUID);
            return true;
        }
        dicePositions[pos1a.getRow()][pos1a.getColumn()] = dice1;
        dicePositions[pos1b.getRow()][pos1b.getColumn()] = dice2;
        dicePositions[pos2a.getRow()][pos2a.getColumn()] = null;
        dicePositions[pos2b.getRow()][pos2b.getColumn()] = null;
        player.game.updateView(player.uUID);
        return false;
    }
}