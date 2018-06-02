package shared;

import java.io.Serializable;

public class Dice implements Serializable {

    public Dice(char color, Integer n){
        this.color = color;
        this.value = n;
    }

    public Integer value;
    public char color;


    public Integer getValue() {
        return value;
    }

    public char getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color+", "+value;
    }
}
