package server.cardsServer.toolC;

import shared.Dice;
import server.Player;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;
import shared.Position;
@Deprecated
public class ToolC9 extends ToolC {

    public ToolC9() {
        this.setName("toolC9");
        this.setDescription("i2 is the index of dice in the draft pool, " +
                "while p1 is the allegedly new position");
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
        if (p1 == null && i2 == null)
            return false;

        return player.getWindow().moveDiceAlone(player, p1, p2);
    }
}