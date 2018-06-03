package server.executable;

import server.Player;
import server.threads.GameManager;
import shared.Dice;
import shared.Overlay;
import shared.Position;
import shared.PositionR;

import java.util.ArrayList;
import java.util.Random;

public class Tool {

    public static Boolean ableAndSettleCard(Player player, Integer i1) {
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

    public static Boolean ableAndSettleDiceAndCard(Player player, Integer i1) {
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

    public static Boolean use1(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettleDiceAndCard(player, i1))
            return false;
        if (i2 == null || i3 == null || p1 == null)
            return false;

        Dice dice = game.getPool().get(i2);

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

        if (player.getWindow().setDiceFromPool(player, i2, p2))
            return true;

        if (i3.equals(-1)) {
            dice.value++;
        } else if (i3.equals(1)) {
            dice.value--;
        }
        return false;
    }
    public static Boolean use2(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettleCard(player, i1))
            return false;
        if (p1 == null || p2 == null)
            return false;
        return player.getWindow().moveDiceNoColor(player, p1, p2);
    }
    public static Boolean use3(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettleCard(player, i1))
            return false;
        if (p1 == null || p2 == null)
            return false;
        return player.getWindow().moveDiceNoShade(player, p1, p2);
    }
    public static Boolean use4(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettleCard(player, i1))
            return false;

        if (p1 == null || p2 == null || p3 == null || p4 == null)
            return false;

        return player.getWindow().moveDice(player, p1, p2, p3, p4);
    }
    public static Boolean use5(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettleDiceAndCard(player, i1))
            return false;
        if (i2 == null || p1 == null || pr == null)
            return false;

        return player.getWindow().moveDiceWindowRoundtrack(game, player, p1, pr);
    }
    public static Boolean use6(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettleDiceAndCard(player, i1))
            return false;
        if (i2 == null || p1 == null)
            return false;

        Dice dice = game.getPool().get(i2);
        Integer value = dice.value;
        Random rand = new Random();
        dice.value = 1 + rand.nextInt(5);

        if (player.getWindow().setDiceFromPool(player, i2, p1))
            return true;
        dice.value = value;
        return false;
    }
    public static Boolean use7(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettleCard(player, i1))
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
    public static Boolean use8(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettleDiceAndCard(player, i1))
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
    public static Boolean use9(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettleDiceAndCard(player, i1))
            return false;
        if (p1 == null && i2 == null)
            return false;

        return player.getWindow().moveDiceAlone(player, p1, p2);
    }
    public static Boolean use10(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettleDiceAndCard(player, i1))
            return false;
        if (i2 == null || p1 == null)
            return false;
        Dice dice = game.getPool().get(i2);
        if (dice == null)
            return false;

        Integer value = game.getPool().get(i2).value;
        if (value == 1)
            game.getPool().get(i2).value = 6;
        else if (value == 2)
            game.getPool().get(i2).value = 5;
        else if (value == 3)
            game.getPool().get(i2).value = 4;
        else if (value == 4)
            game.getPool().get(i2).value = 3;
        else if (value == 5)
            game.getPool().get(i2).value = 2;
        else
            game.getPool().get(i2).value = 1;

        return player.getWindow().setDiceFromPool(player, i2, p1);
    }
    public static Boolean use11(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettleDiceAndCard(player, i1))
            return false;
        if (p1 == null || i2 == null || i3 == null)
            return false;
        if (i2 >= game.getPool().size())
            return false;
        if (game.getPool().get(i2) == null)
            return false;

        Dice dice = null;
        ArrayList<Dice> dices = game.getDices();
        Random rand = new Random();
        int k = 0;
        while (dice == null) {
            k = rand.nextInt(dices.size() - 1);
            dice = dices.get(k);
        }
        dice.value = i3;
        dices.set(k, game.getPool().get(i2));
        game.getPool().set(i2, dice);

        return player.getWindow().setDiceFromPool(player, i2, p1);
    }
    public static Boolean use12(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {

        if (!ableAndSettleCard(player, i1))
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