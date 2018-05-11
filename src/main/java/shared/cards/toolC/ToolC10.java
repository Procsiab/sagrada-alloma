package shared.cards.toolC;

import shared.Dice;
import shared.Player;
import shared.abstracts.ToolC;
import shared.GameManager;

public class ToolC10 extends ToolC {

    private String name = "1";
    private String description = null;


    public void use(GameManager game, Integer n){
        Integer value = game.pool.get(n).value;
        if(value == 1)
            value = 6;
        else if(value == 2)
            value = 5;
        else if(value == 3)
            value = 4;
        else if(value == 4)
            value = 3;
        else if(value ==5)
            value = 2;
        else
            value = 1;
    }
}
