package server.cards;

import server.Dice;
import server.Player;
import server.abstracts.PrivateOC;
import server.threads.GameManager;
import shared.Position;

public class ToolC8 extends PrivateOC {

    private String name = "1";
    private String description = null;


    public void use(Player player, GameManager game, Dice dice, Position position){
        //place that die in the cell. If not return the dice.
    }
}
