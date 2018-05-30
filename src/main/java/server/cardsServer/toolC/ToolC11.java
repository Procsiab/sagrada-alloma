package server.cardsServer.toolC;

import shared.Dice;
import server.Player;
import shared.Position;
import shared.PositionR;
import server.abstractsServer.ToolC;
import server.threads.GameManager;

import java.util.ArrayList;
import java.util.Random;

public class ToolC11 extends ToolC {

    public ToolC11() {
        this.setName("toolC11");
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

        if (!ableAndSettle(player,i1 ))
            return false;

        Dice dice = null;
        ArrayList<Dice> dices =game.getDices();
        Random rand = new Random();
        int k = 0;
        while (dice == null) {
            k = rand.nextInt(dices.size() - 1);
            dice = dices.get(k);
        }
        dices.set(k, game.getPool().get(i2));
        game.getPool().set(i2, dice);

        return player.getWindow().setDiceFromPool(player,i2, p1);
    }
}