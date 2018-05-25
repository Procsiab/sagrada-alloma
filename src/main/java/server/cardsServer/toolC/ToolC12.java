package server.cardsServer.toolC;

import server.Player;
import server.threads.GameManager;
import shared.*;
import server.abstractsServer.ToolC;

public class ToolC12 extends ToolC {

    public ToolC12() {
        this.name = "toolC12";
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
        char color = game.roundTrack.dices.get(pr.column).get(pr.height).color;
        if (color != player.overlay.getDicePositions()[p1.getRow()][p1.getColumn()].color)
            return false;

        Dice[][] dicePositions = player.overlay.getDicePositions();
        Dice dice1 = dicePositions[p1.getRow()][p1.getColumn()];

        //warning
        if(player.window.setDicePositionFromPool(player,0,dice1,p2)){
            dicePositions[p1.getRow()][p1.getColumn()] = null;
            dicePositions[p2.getRow()][p2.getColumn()] = null;
            player.game.updateView(player.uUID);
            return true;
        }
        dicePositions[p1.getRow()][p1.getColumn()] = dice1;
        dicePositions[p2.getRow()][p2.getColumn()] = null;
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

        //warning
        if(player.window.setDicePositionFromPool(player,0,dice1,pos2a)&& player.window.setDicePositionFromPool(player,0,dice2,pos2b)){
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