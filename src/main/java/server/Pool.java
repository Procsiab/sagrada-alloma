package server;

import shared.Dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pool {
    private ArrayList<Dice> poolOfDice = new ArrayList<>();

    public Pool() {
    }

    public List<Dice> getDices() {
        return poolOfDice;
    }

    public Dice getDice(Integer index) {
        return poolOfDice.get(index);
    }

    public void shuffle() {
        Random rand = new Random();
        for (Dice d :
                poolOfDice) {
            if (d != null)
                d.setValue(1 + rand.nextInt(6));
        }
    }

    public void flip(Integer index) {
        Integer value = getDice(index).getValue();
        int i = 1;
        while (i < 7) {
            if (value == i) {
                getDice(index).setValue(7 - i);
                break;
            }
            i++;
        }
    }

    public void clear() {
        poolOfDice.clear();
    }

    public Boolean remove(Dice dice) {
        return poolOfDice.remove(dice);
    }

    public void setDice(Integer index, Dice dice) {
        poolOfDice.set(index, dice);
    }

    public void addDice(Dice dice) {
        poolOfDice.add(dice);
    }

    public Boolean validateBusy(Integer index) {
        return index != null && index > -1 && index < poolOfDice.size() && poolOfDice.get(index) != null;
    }

    @Override
    public String toString() {
        String s = "";
        for (Dice d :
                poolOfDice) {
            s = s + d + "; ";
        }
        return s;
    }
}
