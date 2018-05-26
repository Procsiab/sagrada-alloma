package server.cardsServer.windows;
import shared.Cell;
import server.abstractsServer.Window;

public class Window10 extends Window {

    public Window10(){
        setName("Window10");
        Cell[][] cells = new Cell[4][5];

        //assign of cells


        setCells(cells);
    }
}