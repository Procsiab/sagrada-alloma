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
        this.description = "i2 is the position of the selected dice, " +
                "i3 is the +1 o -1 you want to add to the drafted dice " +
                "p1 is the position where you want your dice to be";
    }

    @Override
    public boolean ableAndSettle(Player player) {
        if (player.hasUsedTc)
            return false;
        if (player.hasPlacedDice)
            return false;
        Integer tokens = player.tokens;
        if (tokens < tokensRequired)
            return false;
        player.tokens = tokens - tokensRequired;
        tokensRequired = 2;
        player.hasUsedTc = true;
        player.hasPlacedDice = true;
        return true;
    }

    @Override
    public boolean use(GameManager game, Player player, Position p1, Position p2,
                       Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettle(player))
            return false;
        if (i2 == null || i3 == null || p1 == null)
            return false;

        Dice dice = game.pool.get(i2);

        if (dice == null)
            return false;

        if (i3.equals(-1)) {
            if (dice.value.equals(1))
                return false;
            dice.value--;
        } else if (i3.equals(1)) {
            if (dice.value.equals(6))
                return false;
            dice.value++;
        } else return false;

        if (player.window.setDicePositionFromPool(player, i2, p2))
            return true;

        if (i3.equals(-1)) {
            dice.value++;
        } else if (i3.equals(1)) {
            dice.value--;
        }
        return false;
    }
}