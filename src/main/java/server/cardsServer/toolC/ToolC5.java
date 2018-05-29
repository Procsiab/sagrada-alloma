package server.cardsServer.toolC;

import server.Player;
import server.threads.GameManager;
import shared.*;
import server.abstractsServer.ToolC;

public class ToolC5 extends ToolC {

    public ToolC5() {
        this.setName("toolC5");
    }

    @Override
    public boolean ableAndSettle(Player player) {
        if(player.hasUsedTc)
            return false;
        Integer tokens = player.tokens;
        if (tokens < this.getTokensRequired())
            return false;
        player.tokens = tokens - this.getTokensRequired();
        this.setTokensRequired(2);
        player.hasUsedTc = true;
        return true;
    }


    public boolean use(GameManager game, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettle(player))
            return false;
        //controllo di tutti i valori in ingresso, null pointer ecc... (even for the remaining TCards)

        /*Dice temp = player.overlay.getDice(player.lastPlaced);
        RoundTrack roundTrack = game.roundTrack;
        player.overlay.setDicePosition(roundTrack.dices.get(pr.column).get(pr.height),player.lastPlaced);
        roundTrack.dices.get(pr.column).set(pr.height, temp);*/

        return false;
    }
}