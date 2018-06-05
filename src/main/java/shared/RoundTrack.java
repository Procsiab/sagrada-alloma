package shared;


import shared.Dice;

import java.io.Serializable;
import java.util.ArrayList;

public class RoundTrack implements Serializable {
    private Integer turn = 0;
    private ArrayList<ArrayList<Dice>> dices = new ArrayList<>(10);

    public RoundTrack() {
        for (int i = 1; i <= 10; i++) {
            dices.add(new ArrayList<>());
        }
    }

    public ArrayList<ArrayList<Dice>> getDices() {
        return dices;
    }

    public boolean busy(PositionR positionR) {
        Dice dice = getDice(positionR);
        if (dice == null)
            return false;
        return true;
    }

    public void setDice(Dice dice, PositionR positionR) {

        this.dices.get(positionR.column).set(positionR.height, dice);
    }

    public void addDice(Dice dice, Integer column) {
        this.dices.get(column).add(dice);
    }

    public Dice getDice(PositionR positionR) {
        if(positionR.column<0||positionR.column>9)
            return null;
        if(positionR.height<0||positionR.height>=dices.get(positionR.column).size())
            return null;
        return this.dices.get(positionR.column).get(positionR.height);
    }

    public Integer sumDices() {
        int sum = 0;
        for (ArrayList<Dice> arr :
                dices) {
            if (arr != null)
                for (Dice dice :
                        arr) {
                    if (dice != null)
                        sum++;
                }
        }
        return sum;
    }
}