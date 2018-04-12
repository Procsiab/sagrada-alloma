package client.logic;

import java.util.Random;

public class Dice {

    public Integer value;
    public char color;
    Integer idDice;


    public Integer getValue() {
        return value;
    }

    public Integer getIDDice() {
        return idDice;
    }

    public char getColor() {
        return color;
    }

    public void setIDDice(Integer idDice) {
        this.idDice = idDice;
    }

    public void shuffle(){
        Random r = new Random();
         value = r.nextInt(6);
    }
}
