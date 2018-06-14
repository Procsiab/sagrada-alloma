package shared;

import java.io.*;

public class Overlay implements Serializable {

    private Dice[][] dicePositions = new Dice[4][5];


    public Overlay(){}

    public Dice getDice(Position pos) {
        if(!pos.validate())
            return null;
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

    public Boolean busy(Position position) {
        if (getDice(position) != null)
            return true;
        return false;
    }

    public Dice[][] getDicePositions() {
        return dicePositions;
    }

    public void setDicePosition(Dice dice, Position position) {
        this.dicePositions[position.getRow()][position.getColumn()] = dice;
    }

}