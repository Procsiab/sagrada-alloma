package server.cards;

import server.Dice;
import server.Player;
import server.abstracts.PrivateOC;
import server.abstracts.ToolC;
import server.threads.GameManager;
import shared.Position;

public class ToolC8 extends ToolC {

    private String name = "1";
    private String description = null;


    public void use(Player player, GameManager game, Dice dice, Position position){
        //place that die in the cell. If not return the dice.
    }
}
