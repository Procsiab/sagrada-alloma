package server.cardsServer.windows;
import shared.Cell;
import server.abstractsServer.Window;

public class Window8 extends Window {

    public Window8(){
        setName("Window8");
        Cell[][] cells = new Cell[4][5];

        //assign of cells


        setCells(cells);
    }
}