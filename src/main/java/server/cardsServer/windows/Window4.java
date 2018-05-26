package server.cardsServer.windows;
import shared.Cell;
import server.abstractsServer.Window;

public class Window4 extends Window {

    public Window4(){
        setName("Window4");
        Cell[][] cells = new Cell[4][5];

        //assign of cells


        setCells(cells);
    }
}