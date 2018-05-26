package server.cardsServer.windows;
import shared.Cell;
import server.abstractsServer.Window;

public class Window16 extends Window {

    public Window16(){
        setName("Window16");
        Cell[][] cells = new Cell[4][5];

        //assign of cells


        setCells(cells);
    }
}