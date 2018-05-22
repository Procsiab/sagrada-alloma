package shared;

import server.threads.GameManager;

import java.io.Serializable;
public class Overlay implements Serializable {
    private Dice[][] dicePositions = new Dice[4][5];


    public synchronized Dice[][] getDicePositions() {
        return dicePositions;
    }

    public synchronized Boolean setDicePositions(Dice dice, Position position) {
        if (dicePositions[position.getRow()][position.getColumn()] == null) {
            this.dicePositions[position.getRow()][position.getColumn()] = dice;
            return true;
        }
        return false;
    }
}