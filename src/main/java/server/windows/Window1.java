package server.windows;

import server.Cell;
import server.abstracts.Window;

public class Window1 extends Window {

    public Window1(Cell[][] feature, Integer tokens){
        this.feature = feature;
        this.tokens = tokens;
    }
}
