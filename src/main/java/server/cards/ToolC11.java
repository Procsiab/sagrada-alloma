package server.cards;

import server.Dice;
import server.Player;
import server.abstracts.PrivateOC;
import server.threads.GameManager;

public class ToolC11 extends PrivateOC {

    private String name = "1";
    private String description = null;


    public void use(Player player, GameManager game, Dice dice){
        //extract new dice from the bag and replace the drafted dice
    }
}
