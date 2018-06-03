package server.cardsServer.toolC;

import server.Player;
import server.threads.GameManager;
import shared.*;
import server.abstractsServer.ToolC;
@Deprecated
public class ToolC12 extends ToolC {

    public ToolC12() {
        this.setName("toolC12");
        this.setDescription("p1 is the currrent position, p2 is the possibly next position " +
                "same applies to p3 and p4, pr is the position on the roundtrack");
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

        if (!ableAndSettle(player, i1))
            return false;
        if (p1 == null || p2 == null || p3 == null || p4 == null)
            return false;

        Dice diceRoundrack = game.getRoundTrack().getDice(pr);
        if (diceRoundrack == null)
            return false;

        Dice dice1 = player.getOverlay().getDice(p1);
        Dice dice2 = player.getOverlay().getDice(p3);
        if (dice1 == null || dice2 == null)
            return false;

        Overlay overlay = player.getOverlay();
        char color = diceRoundrack.color;
        if (color != dice1.color || color != dice2.color)
            return false;
        return player.getWindow().moveDice(player, p1, p2, p3, p4);
    }
}