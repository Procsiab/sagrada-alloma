package shared;

import javafx.geometry.Pos;
import org.junit.experimental.theories.PotentialAssignment;

import java.io.Serializable;

public class Position implements Serializable {
    private static final long serialVersionUID = 1524857704L;
    private Integer row;
    private Integer column;

    public Position(){
    }

    public Position(Integer row, Integer column){
        this.row = row;
        this.column= column;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Position))
            return false;
        Position position= (Position) object;
        if (!position.row.equals( this.row))
            return false;
        if (!position.column.equals(this.column))
            return false;
        return true;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public String toString(){
        return this.getRow()+", "+this.getColumn();
    }
}