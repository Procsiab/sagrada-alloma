package shared;

import java.io.Serializable;

public class Cell implements Serializable {

    private Integer value;
    private Character color;

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

    public Character getColor() {
        return color;
    }
}