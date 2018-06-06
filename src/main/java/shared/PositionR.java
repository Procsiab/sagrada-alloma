package shared;

import javafx.geometry.Pos;

import java.io.Serializable;

public class PositionR implements Serializable {
    private static final long serialVersionUID = 1524857752L;
    public Integer column;
    public Integer height;

    public PositionR(){}
    public PositionR(Integer column, Integer height){
        this.column = column;
        this.height = height;
    }

    @Override
    public String toString() {
        return column+", "+height;
    }
}