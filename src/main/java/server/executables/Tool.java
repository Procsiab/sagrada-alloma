package server.executables;

import server.Overlay;
import server.Player;
import server.Pool;
import server.RoundTrack;
import server.threads.GameManager;
import shared.*;

import java.util.ArrayList;
import java.util.Random;

public class Tool {

    private Tool() {
    }

    /**
     * check whether the player has used his chance to use Toolcard
     *
     * @param player is the current player
     * @param i1     is the index of the card in the @see server.GameManager
     * @return if the player is able or not
     */
    private static Boolean ableCard(Player player, Integer i1) {
        Integer tokens = player.getTokens();
        Integer tokensRequired = player.getGame().getTCtokens(i1);
        return !(player.usedTcQ() || (tokens < tokensRequired));
    }

    /**
     * check whether the player has used his chance to use Toolcard and has already placed a dice
     *
     * @param player is the current player
     * @param i1     is the index of the card in the @see server.GameManager
     * @return if the player is able or not
     */
    private static Boolean ableDiceAndCard(Player player, Integer i1) {
        Integer tokens = player.getTokens();
        Integer tokensRequired = player.getGame().getTCtokens(i1);
        return !(player.usedTcAndPlacedDiceQ() || (tokens < tokensRequired));
    }

    /**
     * @param i1     is the index of the card in the game
     * @param player is the player to whom this effect apply
     * @param p1     is where you want your dice to be placed
     * @param i2     is the position of the dice in the pool
     * @param i3     specifies if you want increment or decrement the shade of your dice
     * @return the result of this operation
     */
    public static Boolean use1(Integer i1, Player player, Position p1, Integer i2, Integer i3) {

        GameManager game = player.getGame();
        Pool pool = game.getPool();
        Overlay overlay = player.getOverlay();

        if (!ableDiceAndCard(player, i1) || i3 == null || !pool.validateBusy(i2) || !overlay.validateEmpty(p1))
            return false;

        Dice dice = pool.getDice(i2);
        if (i3.equals(-1)) {
            if (dice.getValue().equals(1))
                return false;
            dice.setValue(dice.getValue() - 1);
        } else if (i3.equals(1)) {
            if (dice.getValue().equals(6))
                return false;
            dice.setValue(dice.getValue() + 1);
        } else return false;

        return player.getWindow().placeDiceFromPool(player, i2, p1);
    }

    /**
     * @param i1     is the index of the card in the game
     * @param player is the player to whom this effect apply
     * @param p1     is where you want your dice to be taken
     * @param p2     is where you want your dice to be placed, ignoring color restriction
     * @return the result of this operation
     */
    public static Boolean use2(Integer i1, Player player, Position p1, Position p2) {

        Overlay overlay = player.getOverlay();

        if (!ableCard(player, i1) || !overlay.validateBusy(p1) || !overlay.validateEmpty(p2))
            return false;
        return player.getWindow().moveDiceNoColor(player, p1, p2);
    }

    /**
     * @param i1     is the index of the card in the game
     * @param player is the player to whom this effect apply
     * @param p1     is where you want your dice to be taken
     * @param p2     is where you want your dice to be placed, ignoring shade restriction
     * @return the result of this operation
     */
    public static Boolean use3(Integer i1, Player player, Position p1, Position p2) {

        Overlay overlay = player.getOverlay();

        if (!ableCard(player, i1) || !overlay.validateBusy(p1) || !overlay.validateEmpty(p2))
            return false;
        return player.getWindow().moveDiceNoShade(player, p1, p2);
    }

    /**
     * @param i1     is the index of the card in the game
     * @param player is the player to whom this effect apply
     * @param p1     is where you want your dice1 to be taken
     * @param p2     is where you want your dice1 to be placed
     * @param p3     is where you want your dice2 to be taken
     * @param p4     is where you want your dice2 to be placed
     * @return the result of this operation
     */
    public static Boolean use4(Integer i1, Player player, Position p1, Position p2, Position p3, Position p4) {

        Overlay overlay = player.getOverlay();
        if (!ableCard(player, i1) || !overlay.validateBusy(p1) || !overlay.validateEmpty(p2) || !overlay.validateBusy(p3) || !overlay.validateEmpty(p4))
            return false;
        return player.getWindow().moveDice(player, p1, p2, p3, p4);
    }

    /**
     * @param i1     is the index of the card in the game
     * @param player is the player to whom this effect apply
     * @param p1     is where you want your dice to be placed
     * @param i2     is the position of the selected dice in the pool
     * @param pr     is the position of the selected dice on the roundtrack
     * @return the result of this operation
     */
    public static Boolean use5(Integer i1, Player player, Position p1, PositionR pr, Integer i2) {

        GameManager game = player.getGame();
        Pool pool = game.getPool();
        Overlay overlay = player.getOverlay();
        RoundTrack roundTrack = game.getRoundTrack();

        if (!ableDiceAndCard(player, i1) || !pool.validateBusy(i2) || !overlay.validateEmpty(p1) || !roundTrack.validateBusy(pr))
            return false;

        return player.getWindow().moveDiceWindowRoundtrack(player, i2, p1, pr);
    }

    /**
     * @param i1     is the index of the card in the game
     * @param player is the player to whom this effect apply
     * @param p1     is where you want your dice to be placed after having been re-rolled
     * @param i2     is the position of the elected dice in the pool
     * @return the result of this operation
     */
    public static Boolean use6(Integer i1, Player player, Position p1, Integer i2) {

        GameManager game = player.getGame();
        Pool pool = game.getPool();
        Overlay overlay = player.getOverlay();

        if (!ableDiceAndCard(player, i1) || !pool.validateBusy(i2) || !overlay.validateEmpty(p1))
            return false;

        Dice dice = pool.getDice(i2);
        Random rand = new Random();
        dice.setValue(1 + rand.nextInt(6));

        return player.getWindow().placeDiceFromPool(player, i2, p1);


    }

    /**
     * @param i1     is the index of the card in the game
     * @param player is the player to whom this effect apply
     * @return the result of this operation (simply re-roll the pool)
     */
    public static Boolean use7(Integer i1, Player player) {

        GameManager game = player.getGame();
        Pool pool = game.getPool();
        if (!ableCard(player, i1) || player.getPrivateTurn() != 2)
            return false;

        pool.shuffle();
        return true;
    }

    /**
     * @param i1     is the index of the card in the game
     * @param player is the player to whom this effect apply
     * @param p1     is where you want your dice to be placed
     * @param i2     is the position of the dice in the pool
     * @return the result of this operation (place this dice even if you have already placed a dice, skip your next turn though)
     */
    public static Boolean use8(Integer i1, Player player, Position p1, Integer i2) {

        GameManager game = player.getGame();
        Overlay overlay = player.getOverlay();
        Pool pool = game.getPool();
        if (!ableCard(player, i1) || player.getPrivateTurn() > 1 || !overlay.validateEmpty(p1) || !pool.validateBusy(i2))
            return false;

        if (player.getWindow().placeDiceFromPool(player, i2, p1)) {
            game.getJump().add(player.getuUID());
            return true;
        }
        return false;
    }

    /**
     * @param i1     is the index of the card in the game
     * @param player is the player to whom this effect apply
     * @param p1     is where you want your dice to be placed
     * @param i2     is the position of the dice in the pool
     * @return the result of this operation (place the dice in a spot that is not adjacent to any dice)
     */
    public static Boolean use9(Integer i1, Player player, Position p1, Integer i2) {

        GameManager game = player.getGame();
        Overlay overlay = player.getOverlay();
        Pool pool = game.getPool();

        if (!ableDiceAndCard(player, i1) || !overlay.validateEmpty(p1) || !pool.validateBusy(i2))
            return false;

        return player.getWindow().placeDiceAlone(player, i2, p1);
    }

    /**
     * @param i1     is the index of the card in the game
     * @param player is the player to whom this effect apply
     * @param p1     is where you want your dice to be placed
     * @param i2     is the position of the dice in the pool
     * @return the result of this operation (flip a dice and then place it)
     */
    public static Boolean use10(Integer i1, Player player, Position p1, Integer i2) {

        GameManager game = player.getGame();
        Pool pool = game.getPool();
        Overlay overlay = player.getOverlay();
        if (!ableDiceAndCard(player, i1) || !pool.validateBusy(i2) || !overlay.validateEmpty(p1))
            return false;

        pool.flip(i2);

        return player.getWindow().placeDiceFromPool(player, i2, p1);
    }

    /**
     * @param i1     is the index of the card in the game
     * @param player is the player to whom this effect apply
     * @param p1     is where you want your dice to be placed
     * @param i2     is the position of the dice in the pool
     * @param i3     is the number to assign to a randomly extracted dice from the pool, exchanged with the drafted dice
     * @return the result of this operation
     */
    public static Boolean use11(Integer i1, Player player, Position p1, Integer i2, Integer i3) {

        GameManager game = player.getGame();
        ArrayList<Dice> diceBag = game.getDiceBag();
        Pool pool = game.getPool();
        Overlay overlay = player.getOverlay();

        if (!ableDiceAndCard(player, i1) || i3 == null || i3 < 1 || i3 > 6 || !pool.validateBusy(i2) || !overlay.validateEmpty(p1) || diceBag.isEmpty())
            return false;

        Dice dice;
        Random rand = new Random();
        int k = rand.nextInt(diceBag.size());
        dice = diceBag.get(k);
        dice.setValue(i3);
        diceBag.set(k, pool.getDice(i2));
        pool.setDice(i2, dice);

        return player.getWindow().placeDiceFromPool(player, i2, p1);
    }

    /**
     * @param i1     is the index of the card in the game
     * @param player is the player to whom this effect apply
     * @param p1     is where you want your dice1 to be taken
     * @param p2     is where you want your dice1 to be placed
     * @param pr     is the position of the dice in the roundtrack (selected dice must match the color of this dice)
     * @param p3     is where you want your dice2 to be taken
     * @param p4     is where you want your dice2 to be placed
     * @return the result of this operation
     */
    public static Boolean use12(Integer i1, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr) {

        GameManager game = player.getGame();
        Overlay overlay = player.getOverlay();
        RoundTrack roundTrack = game.getRoundTrack();
        Dice diceRoundrack;

        if (!ableCard(player, i1) || !roundTrack.validateBusy(pr))
            return false;
        diceRoundrack = game.getRoundTrack().getDice(pr);

        if (p3 == null && p4 == null) {
            if (!overlay.validateBusy(p1) || !overlay.validateEmpty(p2))
                return false;

            Dice dice1 = overlay.getDice(p1);
            Character color = diceRoundrack.getColor();
            if (!color.equals(dice1.getColor()))
                return false;

            return player.getWindow().moveDice(player, p1, p2);
        }
        if (!overlay.validateBusy(p1) || !overlay.validateEmpty(p2) || !overlay.validateBusy(p3) || !overlay.validateEmpty(p4))
            return false;
        Dice dice1 = player.getOverlay().getDice(p1);
        Dice dice2 = player.getOverlay().getDice(p3);

        Character color = diceRoundrack.getColor();
        if (!color.equals(dice1.getColor()) || !color.equals(dice2.getColor()))
            return false;
        return player.getWindow().moveDice(player, p1, p2, p3, p4);
    }

}