package server.cardsServer.toolC;

import server.Player;
import server.threads.GameManager;
import shared.*;
import server.abstractsServer.ToolC;

public class ToolC12 extends ToolC {

    public ToolC12() {
        this.setName("toolC12");
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


    public boolean use(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettle(player,i1))
            return false;
        //check if null?

        Overlay overlay = player.getOverlay();
        char color = game.getRoundTrack().getDice(pr).color;
        if (color != overlay.getDicePositions()[p1.getRow()][p1.getColumn()].color)
            return false;
        return false;
    }
}