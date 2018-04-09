package ServerP2P.Logic;

import java.util.Random;

public class Dice {

    private Integer value;
    private char color;
    Integer IDDice;


    public Integer getValue() {
        return value;
    }

    public Integer getIDDice() {
        return IDDice;
    }

    public char getColor() {
        return color;
    }

    public void setIDDice(Integer IDDice) {
        this.IDDice = IDDice;
    }

    public void shuffle(){
        Random r = new Random();
         value = r.nextInt(6);
    }
}
