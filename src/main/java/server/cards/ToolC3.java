package server.cards;

import server.Dice;
import server.Player;
import shared.Position;
import server.abstracts.PrivateOC;
import server.threads.GameManager;

public class ToolC3 extends PrivateOC {

    private String name = "3";
    private String description = null;


    public boolean use(Player player, GameManager game, Position position1, Position position2){
        //move any one die in your window regardless only of shade restriction.
        Dice dice = player.frame.getDicePositions()[position1.row][position1.column];
        //check if position is not empty, else return
        if(dice == null)
            return false;
        //move the die
        player.frame.getDicePositions()[position1.row][position1.column] = null;
        player.frame.checkDicePositions2(dice, position2, player);
        return true;
    }
}
