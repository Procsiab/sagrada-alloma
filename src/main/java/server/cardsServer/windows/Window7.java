package server.cardsServer.windows;
import shared.Cell;
import server.abstractsServer.Window;

public class Window7 extends Window {

    public Window7(){
        setName("Window7");
        Cell[][] cells = new Cell[4][5];

        //assign of cells


        setCells(cells);
    }
}