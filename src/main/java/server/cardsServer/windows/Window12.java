package server.cardsServer.windows;
import shared.Cell;
import server.abstractsServer.Window;

public class Window12 extends Window {

    public Window12(){
        setName("Window12");
        Cell[][] cells = new Cell[4][5];

        cells[0][0] = new Cell('r');
        cells[0][1] = new Cell();
        cells[0][2] = new Cell('b');
        cells[0][3] = new Cell();
        cells[0][4] = new Cell('y');
        cells[1][0] = new Cell(4);
        cells[1][1] = new Cell('v');
        cells[1][2] = new Cell(3);
        cells[1][3] = new Cell('g');
        cells[1][4] = new Cell(2);
        cells[2][0] = new Cell();
        cells[2][1] = new Cell(1);
        cells[2][2] = new Cell();
        cells[2][3] = new Cell(5);
        cells[2][4] = new Cell();
        cells[3][0] = new Cell();
        cells[3][1] = new Cell();
        cells[3][2] = new Cell(6);
        cells[3][3] = new Cell();
        cells[3][4] = new Cell();

        setMatrices(cells);
        setTokens(4);
    }
}