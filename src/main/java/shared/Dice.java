package shared;

import java.io.Serializable;

public class Dice implements Serializable {

    public Dice(char color, Integer n){
        this.color = color;
        this.value = n;
    }


    private Integer value;
    private Character color;

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Dice))
            return false;
        Dice dice1 = (Dice) object;
        if (!dice1.value.equals(this.value))
            return false;
        if (!dice1.color.equals(this.color))
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

    public boolean isValid() {
        return !(value == null || color == null || value < 1 || value > 6 || !(color.equals('g')
                || color.equals('b') || color.equals('v') ||
                color.equals('y') || color.equals('r')));
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setColor(Character color) {
        this.color = color;
    }

    public Integer getValue() {
        return value;
    }

    public Character getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color+", "+value;
    }
}
