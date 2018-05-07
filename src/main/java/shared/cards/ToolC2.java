package shared.cards;

import shared.Dice;
import shared.Player;
import shared.abstracts.ToolC;
import shared.Position;
import shared.GameManager;

public class ToolC2 extends ToolC {

    private String name = "2";
    private String description = null;


    public boolean use(Player player, GameManager game, Position position1, Position position2){
        //move any one die in your window regardless only of color restriction.
        Dice dice = player.frame.getDicePositions()[position1.row][position1.column];
        //check if position is not empty, else return
        if(dice == null)
            return false;
        //move the die
        player.frame.getDicePositions()[position1.row][position1.column] = null;
        player.frame.checkDicePositions1(dice, position2, player);
        return true;
    }
}
