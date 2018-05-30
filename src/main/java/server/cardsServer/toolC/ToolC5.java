package server.cardsServer.toolC;

import server.Player;
import server.threads.GameManager;
import shared.*;
import server.abstractsServer.ToolC;

public class ToolC5 extends ToolC {

    public ToolC5() {
        this.setName("toolC5");
        this.setDescription("i2 is the index of dice in the pool, p1 is the position where you want your dice to be" +
                " pr is the position in the roundtrack");
    }

    @Override
    public boolean ableAndSettle(Player player, Integer i1) {
        if (player.usedTcAndPlacedDice())
            return false;
        Integer tokens = player.getTokens();
        Integer tokensRequired = player.getGame().getTCtokens(i1);
        if (tokens < tokensRequired)
            return false;
        player.setTokens(tokens - tokensRequired);
        player.getGame().addTCtokens(i1);
        return true;
    }


    public boolean use(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettle(player, i1))
            return false;
        if (i2 == null || p1== null || pr == null)
            return false;

        return player.getWindow().moveDiceWindowRoundtrack(game,player,p1,pr);
    }
}