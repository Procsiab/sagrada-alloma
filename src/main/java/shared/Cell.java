package shared;

import java.io.Serializable;

public class Cell implements Serializable {

    public Integer value = 0;
    public Character color = 'c';

    public Cell(Integer shade, Character color) {
        this.value = shade;
        this.color = color;
    }
    public Cell(Integer shade) {
        this.value = shade;
    }
    public Cell(Character color) {
        this.color = color;
    }
    public Cell() {
    }

    public Integer getValue() {
        return value;
    }

    public char getColor() {
        return color;
    }

}