package server.cards;

import server.Dice;
import server.Player;
import server.abstracts.PrivateOC;
import server.threads.GameManager;
import shared.PositionR;

public class ToolC5 extends PrivateOC {

    private String name = "1";
    private String description = null;


    public void use(Player player, GameManager game, Dice dice1, PositionR positionR){
        //swap with a dice from Roundtrack
    }
}
