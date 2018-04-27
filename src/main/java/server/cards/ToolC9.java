package server.cards;

import server.Dice;
import server.Player;
import server.abstracts.PrivateOC;
import server.threads.GameManager;
import shared.Position;

public class ToolC9 extends PrivateOC {

    private String name = "1";
    private String description = null;


    public void use(Player player, GameManager game, Dice dice, Position position){
        //place the die, obey all restriction except the one of adjacent position
    }
}
