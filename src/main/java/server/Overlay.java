package server;


import shared.Dice;
import shared.Position;

import java.io.*;

public class Overlay implements Serializable {

    private Dice[][] dicePositions = new Dice[4][5];

    /**
     * check whether the position
     * @param position refer to a in bound dice
     */
    private Boolean validateInBounds(Position position) {
        return position.getRow() > -1 && position.getRow() < 4 && position.getColumn() > -1 && position.getColumn() < 5;
    }

    /**
     * check if
     * @param position is written properly
     */
    private Boolean validateValid(Position position) {
        return position != null && position.getRow() != null && position.getColumn() != null;
    }

    /**
     * check if there is no dice in the position
     * @param position
     */
    public Boolean validateEmpty(Position position) {
        return validateValid(position) && validateInBounds(position) && dicePositions[position.getRow()][position.getColumn()] == null;
    }

    /**
     * check if there is empty space in the position
     * @param position
     */
    public Boolean validateFree(Position position) {
        return validateValid(position) && (!validateInBounds(position) || dicePositions[position.getRow()][position.getColumn()] == null);
    }

    /**
     * check if there is a dice in the position
     * @param position
     */
    public Boolean validateBusy(Position position) {
        return validateValid(position) && validateInBounds(position) && dicePositions[position.getRow()][position.getColumn()] != null;
    }

    public Dice getDice(Position pos) {
        return dicePositions[pos.getRow()][pos.getColumn()];
    }

    public String toString() {
        int i = 0;
        int j = 0;

        String str = "";

        while (i < 4) {
            while (j < 5) {
                str = str + dicePositions[i][j] + "; ";
                j++;
            }
            str = str + "\n";
            i++;
            j = 0;
        }
        return str;
    }

    public Dice[][] getDicePositions() {
        return this.dicePositions;
    }

    public void setDicePosition(Dice dice, Position position) {
        this.dicePositions[position.getRow()][position.getColumn()] = dice;
    }

}