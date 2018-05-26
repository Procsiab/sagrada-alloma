package server.cardsServer.windows;
import shared.Cell;
import server.abstractsServer.Window;

public class Window9 extends Window {

    public Window9(){
        setName("Window9");
        Cell[][] cells = new Cell[4][5];

        //assign of cells


        setCells(cells);
    }
}