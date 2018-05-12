package shared;

public class Overlay {
    private Dice[][] dicePositions = new Dice[4][5];

    public synchronized Dice[][] getDicePositions() {
        return dicePositions;
    }

    public synchronized void setDicePositions(Dice dice, Position position) {
        this.dicePositions[position.getRow()][position.getColumn()] = dice;
    }
}