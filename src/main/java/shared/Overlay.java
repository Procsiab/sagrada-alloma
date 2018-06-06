package shared;

import java.io.*;

public class Overlay implements Serializable {

    private Dice[][] dicePositions = new Dice[4][5];


    public Overlay() {
    }

    public Overlay(Dice[][] dices) {
        dicePositions = dices;
    }

    public Dice getDice(Position pos) {
        if(pos.getRow()>3 ||pos.getRow()<0 ||pos.getColumn()>4||pos.getColumn()<0)
            return null;
        return dicePositions[pos.getRow()][pos.getColumn()];
    }

    public void setDices(Dice[][] dices) {
        this.dicePositions = dices;
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

    public boolean busy(Position position) {
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