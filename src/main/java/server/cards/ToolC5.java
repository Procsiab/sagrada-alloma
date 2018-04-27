package server.cards;

import server.Dice;
import server.Player;
import server.abstracts.PrivateOC;
import server.abstracts.ToolC;
import server.threads.GameManager;
import shared.PositionR;

import javax.tools.Tool;

public class ToolC5 extends ToolC {

    private String name = "1";
    private String description = null;


    public void use(Player player, GameManager game, Dice dice1, PositionR positionR){
        //swap with a dice from Roundtrack
    }
}
