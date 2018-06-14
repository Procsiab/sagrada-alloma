package shared;

import server.threads.MainServer;

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
        if (!(object instanceof Position)||!((Position) object).validate())
            return false;
        Position position= (Position) object;
        if (!position.row.equals( this.row)||!position.column.equals(this.column))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return MainServer.primeNumber(row) * MainServer.primeNumber(row * column);
    }

    public boolean validate() {
        return !(row == null || column == null || row < 0 || row > 3 || column < 0 || column > 4);
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