package shared.cards.toolC;

import shared.*;
import shared.abstracts.ToolC;

public class ToolC5 extends ToolC {

    private String name = "1";
    private String description = null;


    public void use(GameManager game, Integer pos, PositionR positionR){
        Dice temp=game.pool.get(pos);
        RoundTrack roundTrack = game.roundTrack;
        game.pool.set(pos, roundTrack.dices.get(positionR.column).get(positionR.height));
        roundTrack.dices.get(positionR.column).set(positionR.height,temp);
    }
}
