package server.cardsServer.toolC;

import shared.Dice;
import server.Player;
import shared.Position;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;

import java.util.Random;

public class ToolC6 extends ToolC {
    public ToolC6() {
        this.setName("toolC6");
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

    @Override
    public boolean use(GameManager game, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettle(player))
            return false;

        return false;
    }
}