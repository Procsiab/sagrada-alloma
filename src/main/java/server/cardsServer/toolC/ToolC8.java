package server.cardsServer.toolC;

import server.Player;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;
import shared.Position;

public class ToolC8 extends ToolC {

    public ToolC8() {
        this.setName("toolC8");
        this.setDescription("i2 is the position of the dice and p1 is the possibly final position of the dice");
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

    @Override
    public boolean use(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettle(player, i1))
            return false;

        if (player.getPrivateTurn() == 1)
            return false;

        if (i2 == null || p1 == null)
            return false;

        if (player.getWindow().setDiceFromPool(player, i2, p1)) {
            game.getJump().add(player.getuUID());
            return true;
        }
        return false;
    }
}