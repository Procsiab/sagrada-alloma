package shared.TransferObjects;

import shared.Cell;

import java.io.Serializable;

public class WindowT implements Serializable {
    public final String name;
    public final Cell[][] cells;

    public WindowT(String name, Cell[][] cells) {
        this.name = name;
        this.cells = cells;
    }

    public String getName(){
        return this.name;
    }
}
