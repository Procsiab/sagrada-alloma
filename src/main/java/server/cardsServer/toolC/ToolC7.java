package server.cardsServer.toolC;

import shared.Dice;
import server.Player;
import shared.Position;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;

import java.util.Random;

public class ToolC7 extends ToolC {
    public ToolC7() {
        this.setName("toolC7");
        this.setDescription("no parameters required");
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

        if (player.getPrivateTurn() == 1)
            return false;

        Random rand = new Random();
        for (Dice d :
                game.getPool()) {
            if (d != null)
                d.value = 1 + rand.nextInt(5);
        }
        return true;
    }
}