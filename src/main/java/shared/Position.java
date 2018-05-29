package shared;

import java.io.Serializable;

public class Position implements Serializable {
    private static final long serialVersionUID = 1524857704L;
    public Integer row;
    public Integer column;

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
}