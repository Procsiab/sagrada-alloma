package server;

import shared.Dice;
import shared.PositionR;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoundTrack implements Serializable {
    private List<ArrayList<Dice>> dices = new ArrayList<>();

    public RoundTrack() {
        for (int i = 1; i <= 10; i++) {
            dices.add(new ArrayList<>());
        }
    }

    public List<ArrayList<Dice>> getDices() {
        return dices;
    }

    public Boolean validateBusy(PositionR positionR) {
        return positionR != null && positionR.getColumn() != null && positionR.getHeight() != null && positionR.getColumn() > -1 && positionR.getColumn() < dices.size() && positionR.getHeight() > -1 && positionR.getHeight() < dices.get(positionR.getColumn()).size();
    }

    public void setDice(Dice dice, PositionR positionR) {
        this.dices.get(positionR.getColumn()).set(positionR.getHeight(), dice);
    }

    public void addDice(Dice dice, Integer column) {
        this.dices.get(column).add(dice);
    }

    public Dice getDice(PositionR positionR) {
        return this.dices.get(positionR.getColumn()).get(positionR.getHeight());
    }

}