package shared;

import java.io.*;

public class Overlay implements Serializable {

    private Dice[][] dicePositions = new Dice[4][5];


    public Overlay(){}
    public Overlay(Dice[][] dices){
        dicePositions = dices;
    }

    public Dice getDice(Position pos) {
        return dicePositions[pos.getRow()][pos.getColumn()];
    }
    public void setDices(Dice[][] dices) {
        this.dicePositions = dices;
    }
    public boolean busy(Position position) {
        if (dicePositions[position.getRow()][position.getColumn()] != null)
            return false;
        return true;
    }
    public boolean busy(Position position1, Position position2) {
        return busy(position1) && busy(position2);
    }
    public Dice[][] getDicePositions() {
        return dicePositions;
    }
    public void setDicePosition(Dice dice, Position position) {
        this.dicePositions[position.getRow()][position.getColumn()] = dice;
    }
    public Overlay deepClone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Overlay) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}