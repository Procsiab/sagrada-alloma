package shared.cards.toolC;

import shared.Dice;
import shared.Player;
import shared.abstracts.ToolC;
import shared.GameManager;
import shared.Position;

public class ToolC9 extends ToolC {

    private String name = "1";
    private String description = null;


    public void use(Player player, GameManager game, Dice dice, Position position){
        //place the die, obey all restriction except the one of adjacent position
    }
}
