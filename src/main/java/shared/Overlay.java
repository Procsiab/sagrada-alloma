package shared;


import java.io.*;

public class Overlay implements Serializable {

    private Dice[][] dicePositions = new Dice[4][5];

    private Boolean validateInBounds(Position position) {
        return position.getRow() > -1 && position.getRow() < 4 && position.getColumn() > -1 && position.getColumn() < 5;
    }

    private Boolean validateValid(Position position) {
        return position != null && position.getRow() != null && position.getColumn() != null;
    }

    public Boolean validateEmpty(Position position) {
        return validateValid(position) && validateInBounds(position) && dicePositions[position.getRow()][position.getColumn()] == null;
    }

    public Boolean validateFree(Position position) {
        return validateValid(position) && (!validateInBounds(position) || dicePositions[position.getRow()][position.getColumn()] == null);
    }

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