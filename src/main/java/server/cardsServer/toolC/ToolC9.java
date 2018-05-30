package server.cardsServer.toolC;

import shared.Dice;
import server.Player;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;
import shared.Position;

public class ToolC9 extends ToolC {

    public ToolC9() {
        this.setName("toolC9");
    }

    @Override
    public boolean ableAndSettle(Player player, Integer i1) {
        if (player.usedTc())
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

        if (!ableAndSettle(player,i1 ))
            return false;
        Dice dice = game.getPool().get(i2);

        if (!player.getWindow().checkEdgePosTurn(player, p1))
            return false;
        if (!player.getWindow().checkPlaceRequirements(dice, p1))
            return false;

        //check if in the surroundings there is not an other dice

        player.getOverlay().getDicePositions()[p1.getRow()][p1.getColumn()] = dice;
        game.getPool().set(i2, null);
        return true;
    }
}