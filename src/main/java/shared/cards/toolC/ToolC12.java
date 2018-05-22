package shared.cards.toolC;

import server.Player;
import server.threads.GameManager;
import shared.*;
import shared.abstracts.ToolC;

public class ToolC12 extends ToolC {

    private String name = "1";
    private String description = null;


    public boolean use(Player player, GameManager game, Position pos1, Position pos2, PositionR posR) {

        if(!checkIfEmpty())
            return false;
        if (!checkIfAble())
            return false;

        char color = game.roundTrack.dices.get(posR.column).get(posR.height).color;
        if (color != player.overlay.getDicePositions()[pos1.getRow()][pos1.getColumn()].color)
            return false;

        Dice[][] dicePositions = player.overlay.getDicePositions();
        Dice dice1 = dicePositions[pos1.getRow()][pos1.getColumn()];

        if(player.window.setDicePositionFromPool(player,dice1,pos2)){
            dicePositions[pos1.getRow()][pos1.getColumn()] = null;
            dicePositions[pos2.getRow()][pos2.getColumn()] = null;
            player.game.updateView(player.uUID);
            return true;
        }
        dicePositions[pos1.getRow()][pos1.getColumn()] = dice1;
        dicePositions[pos2.getRow()][pos2.getColumn()] = null;
        player.game.updateView(player.uUID);
        return false;
    }

    public boolean use(Player player, GameManager game, Position pos1a, Position pos2a, Position pos1b, Position pos2b, PositionR posR) {
        char color = game.roundTrack.dices.get(posR.column).get(posR.height).color;
        if (color != player.overlay.getDicePositions()[pos1a.getRow()][pos1a.getColumn()].color
                || color != player.overlay.getDicePositions()[pos2a.getRow()][pos2a.getColumn()].color)
            return false;

        Dice[][] dicePositions = player.overlay.getDicePositions();
        Dice dice1 = dicePositions[pos1a.getRow()][pos1a.getColumn()];
        Dice dice2 = dicePositions[pos2a.getRow()][pos2a.getColumn()];

        if(player.window.setDicePositionFromPool(player,dice1,pos2a)&& player.window.setDicePositionFromPool(player,dice2,pos2b)){
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