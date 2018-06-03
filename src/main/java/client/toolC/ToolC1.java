package server.cardsServer.toolC;

import server.Player;
import shared.Dice;
import shared.Position;
import shared.PositionR;
import server.threads.GameManager;
@Deprecated
public class ToolC1 extends{

    public ToolC1() {
        this.setName("toolC1");
        this.setDescription("i2 is the position of the selected dice, " +
                "i3 is the +1 o -1 you want to add to the drafted dice " +
                "p1 is the position where you want your dice to be");
    }
}