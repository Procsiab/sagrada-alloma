package shared.cards.toolC;

import shared.Dice;
import shared.Player;
import shared.abstracts.ToolC;
import shared.Position;
import shared.GameManager;

public class ToolC1 extends ToolC {

    private String name = "1";
    private String description = null;


    public boolean use(GameManager game, Integer pos, Integer diff) {
        Dice dice = game.pool.get(pos);

        if (diff.equals(-1)) {
            if (dice.value.equals(1))
                return false;
            dice.value--;
            return true;
        } else if (diff.equals(1)) {
            if (dice.value.equals(6))
                return false;
            dice.value++;
            return true;
        }
        return false;
    }
}