package server.cardsServer.toolC;

import server.Player;
import shared.Dice;
import shared.Position;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;

public class ToolC1 extends ToolC {

    public ToolC1() {
        this.name = "toolC1";
    }

    @Override
    public boolean ableAndSettle(Player player) {
        Integer tokens = player.tokens;
        if (tokens < tokensRequired)
            return false;
        player.tokens = tokens - tokensRequired;
        tokensRequired = 2;
        player.hasUsedTc = true;
        return true;
    }

    @Override
    public boolean use(GameManager game, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettle(player))
            return false;

        Dice dice = game.pool.get(i2);
        if (i3.equals(-1)) {
            if (dice.value.equals(1)) {
                return false;
            }
            dice.value--;
            return true;
        } else if (i3.equals(1)) {
            if (dice.value.equals(6)) {
                return false;
            }
            dice.value++;
            return true;
        }
        return false;
    }
}