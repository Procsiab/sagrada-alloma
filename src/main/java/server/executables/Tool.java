package server.executables;

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
        Integer tokens = player.getTokens();
        Integer tokensRequired = player.getGame().getTCtokens(i1);
        if (player.usedTc() || (tokens < tokensRequired))
            return false;
        player.setTokens(tokens - tokensRequired);
        player.getGame().addTCtokens(i1);
        return true;
    }

    public static Boolean ableAndSettleDiceAndCard(Player player, Integer i1) {
        Integer tokens = player.getTokens();
        Integer tokensRequired = player.getGame().getTCtokens(i1);
        if (player.usedTcAndPlacedDice() || (tokens < tokensRequired))
            return false;
        player.setTokens(tokens - tokensRequired);
        player.getGame().addTCtokens(i1);
        return true;
    }

    public static Boolean use1(GameManager game, Integer i1, Player player, Position p1, Integer i2, Integer i3) {

        if (!ableAndSettleDiceAndCard(player, i1) ||
                i2 == null || i3 == null || p1 == null
                || i2 >= game.getPool().size() || i2 < 0)
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

        if (player.getWindow().setDiceFromPool(player, i2, p1))
            return true;

        if (i3.equals(-1)) {
            dice.value++;
        } else if (i3.equals(1)) {
            dice.value--;
        }
        return false;
    }

    public static Boolean use2(Integer i1, Player player, Position p1, Position p2) {

        if (!ableAndSettleCard(player, i1) || p1 == null || p2 == null)
            return false;
        return player.getWindow().moveDiceNoColor(player, p1, p2);
    }

    public static Boolean use3(Integer i1, Player player, Position p1, Position p2) {

        if (!ableAndSettleCard(player, i1) || p1 == null || p2 == null)
            return false;
        return player.getWindow().moveDiceNoShade(player, p1, p2);
    }

    public static Boolean use4(Integer i1, Player player, Position p1, Position p2, Position p3, Position p4) {

        if (!ableAndSettleCard(player, i1) ||
                p1 == null || p2 == null || p3 == null || p4 == null)
            return false;

        return player.getWindow().moveDice(player, p1, p2, p3, p4);
    }

    public static Boolean use5(GameManager game, Integer i1, Player player, Position p1, PositionR pr, Integer i2) {

        if (!ableAndSettleDiceAndCard(player, i1) || i2 == null || p1 == null || pr == null)
            return false;

        return player.getWindow().moveDiceWindowRoundtrack(game, player, i2, p1, pr);
    }

    public static Boolean use6(GameManager game, Integer i1, Player player, Position p1, Integer i2) {

        if (!ableAndSettleDiceAndCard(player, i1) || i2 == null || p1 == null)
            return false;

        Dice dice = game.getPool().get(i2);
        Integer value = dice.value;
        Random rand = new Random();
        dice.value = 1 + rand.nextInt(5);

        if (player.getWindow().setDiceFromPool(player, i2, p1))
            return true;
        //dice.value = value;
        return false;
    }

    public static Boolean use7(GameManager game, Integer i1, Player player) {

        if (!ableAndSettleCard(player, i1) || player.getPrivateTurn() == 1)
            return false;

        Random rand = new Random();
        for (Dice d :
                game.getPool()) {
            if (d != null)
                d.value = 1 + rand.nextInt(5);
        }
        return true;
    }

    public static Boolean use8(GameManager game, Integer i1, Player player, Position p1, Integer i2) {

        if (!ableAndSettleCard(player, i1) || player.getPrivateTurn() == 1
                || i2 == null || p1 == null)
            return false;

        if (player.getWindow().setDiceFromPool(player, i2, p1)) {
            game.getJump().add(player.getuUID());
            return true;
        }
        return false;
    }

    public static Boolean use9(Integer i1, Player player, Position p1, Position p2, Integer i2) {

        if (!ableAndSettleDiceAndCard(player, i1) || p1 == null && i2 == null)
            return false;

        return player.getWindow().moveDiceAlone(player, p1, p2);
    }

    public static Boolean use10(GameManager game, Integer i1, Player player, Position p1, Integer i2) {

        if (!ableAndSettleDiceAndCard(player, i1) || i2 == null || p1 == null ||
                i2 < 0 || i2 >= game.getPool().size())
            return false;

        Dice dice = game.getPool().get(i2);
        if (dice == null)
            return false;

        Integer value = game.getPool().get(i2).value;
        int i = 1;
        while (i < 7) {
            if (value == i) {
                game.getPool().get(i2).value = 7 - i;
                break;
            }
            i++;
        }

        return player.getWindow().setDiceFromPool(player, i2, p1);
    }

    public static Boolean use11(GameManager game, Integer i1, Player player, Position p1, Integer i2, Integer i3) {

        if (!ableAndSettleDiceAndCard(player, i1) || p1 == null || i2 == null
                || i3 == null || i2 >= game.getPool().size() || i2 < 0
                || game.getPool().get(i2) == null)
            return false;

        Dice dice;
        ArrayList<Dice> dices = game.getDices();
        if (dices.isEmpty())
            return false;

        Random rand = new Random();
        int k = rand.nextInt(dices.size() - 1);
        dice = dices.get(k);
        dice.value = i3;
        dices.set(k, game.getPool().get(i2));
        game.getPool().set(i2, dice);

        return player.getWindow().setDiceFromPool(player, i2, p1);
    }

    public static Boolean use12(GameManager game, Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr) {

        if (!ableAndSettleCard(player, i1) || p1 == null || p2 == null ||
                game.getRoundTrack().getDice(pr) == null)
            return false;

        Dice diceRoundrack = game.getRoundTrack().getDice(pr);

        if (p3 == null && p4 == null) {
            Dice dice1 = player.getOverlay().getDice(p1);
            if (dice1 == null)
                return false;
            char color = diceRoundrack.color;
            if (color != dice1.color)
                return false;

            return player.getWindow().moveDice(player, p1, p2);
        }
        if (p3 == null || p4 == null)
            return false;
        Dice dice1 = player.getOverlay().getDice(p1);
        Dice dice2 = player.getOverlay().getDice(p3);
        if (dice1 == null || dice2 == null)
            return false;

        char color = diceRoundrack.color;
        if (color != dice1.color || color != dice2.color)
            return false;
        return player.getWindow().moveDice(player, p1, p2, p3, p4);
    }

}