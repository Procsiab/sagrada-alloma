package shared;

import java.io.Serializable;

public class Dice implements Serializable {

    public Dice(char color, Integer n){
        this.color = color;
        this.value = n;
    }

    public Integer value;
    public char color;

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Dice))
            return false;
        Dice dice1 = (Dice) object;
        if (dice1.value != this.value)
            return false;
        if (dice1.color != this.color)
            return false;
        return true;
    }

    public boolean isCloseTo(Dice dice){
        if(dice.color == this.color)
            return true;
        if(dice.value== this.value)
            return true;
        return false;
    }

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
