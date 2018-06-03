package server;

import server.Player;
import server.SReferences;
import server.threads.GameManager;
import shared.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Window implements Serializable {

    private final Cell[][] matrices;
    private final Integer tokens;
    private final String name;

    public Window(Cell[][] matrices, String name, Integer tokens){
        this.name=name;
        this.matrices = matrices;
        this.tokens = tokens;
    }

    public Cell[][] getMatrices() {
        return matrices;
    }

    public Integer getTokens() {
        return tokens;
    }

    public String getName() {
        return name;
    }

    public boolean checkEdgePosTurn(Player player, Position position) {
        if (player.getTurno() != 1)
            return true;
        if (position.getRow() == 0 || position.getRow() == 3 || position.getColumn() == 0 || position.getColumn() == 4)
            return true;
        return false;
    }

    public boolean checkAdjDicesFull(Overlay overlay, Position position1, Dice dice1) {
        int i, j;
        boolean esito;
        Position position = new Position();
        Dice dice;

        for (i = 0; i < 4; i++) {
            for (j = 0; j < 5; j++) {

                if (overlay.getDicePositions()[i][j] != null) {
                    position.setColumn(j);
                    position.setRow(i);
                    esito = false;
                    dice = overlay.getDicePositions()[i][j - 1];
                    if (position.getRow() >= 0 && position.getColumn() - 1 >= 0)
                        if (position1.getRow().equals(position.getRow()) &&
                                position1.getColumn().equals(position.getColumn() - 1))
                            if (dice1.color != dice.color && dice1.value != dice.value)
                                esito = true;

                    if (position.getRow() - 1 >= 0 && position.getColumn() - 1 >= 0)
                        if (position1.getRow().equals(position.getRow() - 1) &&
                                position1.getColumn().equals(position.getColumn() - 1))
                            esito = true;

                    dice = overlay.getDicePositions()[i - 1][j];
                    if (position.getRow() - 1 >= 0 && position.getColumn() >= 0)
                        if (position1.getRow().equals(position.getRow() - 1) &&
                                position1.getColumn().equals(position.getColumn()))
                            if (dice1.color != dice.color && dice1.value != dice.value)
                                esito = true;

                    if (position.getRow() - 1 >= 0 && position.getColumn() + 1 <= 4)
                        if (position1.getRow().equals(position.getRow() - 1) &&
                                position1.getColumn().equals(position.getColumn() + 1))
                            esito = true;

                    dice = overlay.getDicePositions()[i][j + 1];
                    if (position.getRow() >= 0 && position.getColumn() + 1 <= 4)
                        if (position1.getRow().equals(position.getRow()) &&
                                position1.getColumn().equals(position.getColumn() + 1))
                            if (dice1.color != dice.color && dice1.value != dice.value)
                                esito = true;

                    if (position.getRow() + 1 <= 3 && position.getColumn() + 1 <= 4)
                        if (position1.getRow().equals(position.getRow() + 1) &&
                                position1.getColumn().equals(position.getColumn() + 1))
                            esito = true;

                    dice = overlay.getDicePositions()[i + 1][j];
                    if (position.getRow() + 1 <= 3 && position.getColumn() >= 0)
                        if (position1.getRow().equals(position.getRow() + 1) &&
                                position1.getColumn().equals(position.getColumn()))
                            if (dice1.color != dice.color && dice1.value != dice.value)
                                esito = true;

                    if (position.getRow() + 1 <= 3 && position.getColumn() - 1 >= 0)
                        if (position1.getRow().equals(position.getRow() + 1) &&
                                position1.getColumn().equals(position.getColumn() - 1))
                            esito = true;

                    if (!esito)
                        return false;
                }
            }
        }
        return true;
    }

    public boolean checkAdjDices(Overlay overlay, Position position1) {
        int i, j;
        boolean esito;
        Position position = new Position();

        for (i = 0; i < 4; i++) {
            for (j = 0; j < 5; j++) {
                if (overlay.getDicePositions()[i][j] != null) {
                    position.setColumn(j);
                    position.setRow(i);
                    esito = false;

                    if (position.getRow() >= 0 && position.getColumn() - 1 >= 0)
                        if (position1.getRow().equals(position.getRow()) &&
                                position1.getColumn().equals(position.getColumn() - 1))
                            esito = true;

                    if (position.getRow() - 1 >= 0 && position.getColumn() - 1 >= 0)
                        if (position1.getRow().equals(position.getRow() - 1) &&
                                position1.getColumn().equals(position.getColumn() - 1))
                            esito = true;

                    if (position.getRow() - 1 >= 0 && position.getColumn() >= 0)
                        if (position1.getRow().equals(position.getRow() - 1) &&
                                position1.getColumn().equals(position.getColumn()))
                            esito = true;

                    if (position.getRow() - 1 >= 0 && position.getColumn() + 1 <= 4)
                        if (position1.getRow().equals(position.getRow() - 1) &&
                                position1.getColumn().equals(position.getColumn() + 1))
                            esito = true;

                    if (position.getRow() >= 0 && position.getColumn() + 1 <= 4)
                        if (position1.getRow().equals(position.getRow()) &&
                                position1.getColumn().equals(position.getColumn() + 1))
                            esito = true;

                    if (position.getRow() + 1 <= 3 && position.getColumn() + 1 <= 4)
                        if (position1.getRow().equals(position.getRow() + 1) &&
                                position1.getColumn().equals(position.getColumn() + 1))
                            esito = true;

                    if (position.getRow() + 1 <= 3 && position.getColumn() >= 0)
                        if (position1.getRow().equals(position.getRow() + 1) &&
                                position1.getColumn().equals(position.getColumn()))
                            esito = true;

                    if (position.getRow() + 1 <= 3 && position.getColumn() - 1 >= 0)
                        if (position1.getRow().equals(position.getRow() + 1) &&
                                position1.getColumn().equals(position.getColumn() - 1))
                            esito = true;

                    if (!esito)
                        return false;
                }
            }
        }
        return true;
    }

    public boolean notAdjacentToAny(Overlay overlay, Position position1) {
        Position position = new Position(position1.getRow() - 1, position1.getColumn() - 1);
        if (position.getRow() >= 0 && position.getColumn() >= 0)
            if (overlay.busy(position))
                return false;
        position = new Position(position1.getRow() - 1, position1.getColumn());
        if (position.getRow() >= 0 && position.getColumn() >= 0)
            if (overlay.busy(position))
                return false;
        position = new Position(position1.getRow() - 1, position1.getColumn() + 1);
        if (position.getRow() >= 0 && position.getColumn() >= 0)
            if (overlay.busy(position))
                return false;
        position = new Position(position1.getRow(), position1.getColumn() + 1);
        if (position.getRow() >= 0 && position.getColumn() >= 0)
            if (overlay.busy(position))
                return false;
        position = new Position(position1.getRow() + 1, position1.getColumn() + 1);
        if (position.getRow() >= 0 && position.getColumn() >= 0)
            if (overlay.busy(position))
                return false;
        position = new Position(position1.getRow() + 1, position1.getColumn());
        if (position.getRow() >= 0 && position.getColumn() >= 0)
            if (overlay.busy(position))
                return false;
        position = new Position(position1.getRow() + 1, position1.getColumn() - 1);
        if (position.getRow() >= 0 && position.getColumn() >= 0)
            if (overlay.busy(position))
                return false;
        position = new Position(position1.getRow(), position1.getColumn() - 1);
        if (position.getRow() >= 0 && position.getColumn() >= 0)
            if (overlay.busy(position))
                return false;
        return true;
    }

    public boolean checkPlaceColorRequirements(Dice dice, Position position) {
        if (this.getMatrices()[position.getRow()][position.getColumn()] == null)
            return true;
        if (dice.getColor() != this.getMatrices()[position.getRow()][position.getColumn()].getColor()) {
            return false;
        }
        return true;
    }

    public boolean checkPlaceValueRequirements(Dice dice, Position position) {
        if (this.getMatrices()[position.getRow()][position.getColumn()] == null)
            return true;
        if (dice.getValue() != this.getMatrices()[position.getRow()][position.getColumn()].getValue()) {
            return false;
        }
        return true;
    }

    public boolean checkPlaceRequirements(Dice dice, Position position) {
        return checkPlaceValueRequirements(dice, position) && checkPlaceColorRequirements(dice, position);
    }

    public boolean setDiceFromPool(Player player, Integer index, Position position) {

        if (index == null || position == null)
            return false;

        ArrayList<Dice> pool = player.getGame().getPool();
        if (index >= pool.size())
            return false;

        Dice dice = pool.get(index);
        if (dice == null)
            return false;

        if (player.getOverlay().busy(position))
            return false;


        if (!checkDice(player, dice, position))
            return false;

        player.getOverlay().setDicePosition(dice, position);
        pool.set(index, null);
        return true;
    }

    public boolean moveDice(Player player, Position p1, Position p2) {

        if (p1 == null || p2 == null)
            return false;

        Dice dice = player.getOverlay().getDice(p1);
        if (dice == null)
            return false;

        if (player.getOverlay().busy(p2))
            return false;

        if (checkDice(player, dice, p2)) {
            player.getOverlay().setDicePosition(dice, p2);
            player.getOverlay().setDicePosition(null, p1);
            return true;
        }
        return false;
    }

    public boolean moveDice(Player player, Position p1, Position p2, Position p3, Position p4) {

        if (p1 == null || p2 == null || p3 == null || p4 == null)
            return false;

        Overlay overlay = player.getOverlay();

        Dice dice1 = overlay.getDice(p1);
        Dice dice2 = overlay.getDice(p2);
        if (dice1 == null || dice2 == null)
            return false;

        if (overlay.busy(p2, p4))
            return false;

        if (checkDice(player, dice1, p2)) {
            overlay.setDicePosition(dice1, p2);
            overlay.setDicePosition(null, p1);
            if (checkDice(player, dice2, p4)) {
                overlay.setDicePosition(dice2, p4);
                overlay.setDicePosition(null, p3);
                return true;
            }
        }
        overlay.setDicePosition(dice1, p1);
        overlay.setDicePosition(null, p2);
        overlay.setDicePosition(dice2, p3);
        overlay.setDicePosition(null, p4);
        return false;
    }

    public boolean checkDice(Player player, Dice dice, Position position) {
        Overlay overlay = player.getOverlay();


        if (!checkEdgePosTurn(player, position))
            return false;


        if (!checkAdjDicesFull(overlay, position, dice))
            return false;

        if (!checkPlaceRequirements(dice, position))
            return false;
        return true;
    }

    public boolean moveDiceNoShade(Player player, Position p1, Position p2) {

        if (p1 == null || p2 == null)
            return false;

        Overlay overlay = player.getOverlay();
        Dice dice = overlay.getDice(p1);
        if (dice == null)
            return false;

        if (overlay.busy(p2))
            return false;

        if (checkDiceNoShade(player, dice, p2)) {
            overlay.setDicePosition(dice, p2);
            overlay.setDicePosition(null, p1);
            return true;
        }
        return false;
    }

    public boolean checkDiceNoShade(Player player, Dice dice, Position position) {
        Overlay overlay = player.getOverlay();


        if (!checkEdgePosTurn(player, position))
            return false;


        if (!checkAdjDicesFull(overlay, position, dice))
            return false;

        if (!checkPlaceColorRequirements(dice, position))
            return false;
        return true;
    }

    public boolean moveDiceNoColor(Player player, Position p1, Position p2) {

        if (p1 == null || p2 == null)
            return false;

        Overlay overlay = player.getOverlay();
        Dice dice = overlay.getDice(p1);
        if (dice == null)
            return false;

        if (overlay.busy(p2))
            return false;

        if (checkDiceNoColor(player, dice, p2)) {
            overlay.setDicePosition(dice, p2);
            overlay.setDicePosition(null, p1);
            return true;
        }
        return false;
    }

    public boolean checkDiceNoColor(Player player, Dice dice, Position position) {
        Overlay overlay = player.getOverlay();


        if (!checkEdgePosTurn(player, position))
            return false;


        if (!checkAdjDicesFull(overlay, position, dice))
            return false;

        if (!checkPlaceValueRequirements(dice, position))
            return false;
        return true;
    }

    public boolean moveDiceWindowRoundtrack(GameManager gameManager, Player player, Position p1, PositionR pr) {

        RoundTrack roundTrack = gameManager.getRoundTrack();
        if (roundTrack.busy(pr))
            return false;
        if (player.getOverlay().busy(p1))
            return false;
        //must obey placement restriction

        Dice diceRoundtrack = roundTrack.getDice(pr);

        if (!checkDice(player, diceRoundtrack, p1))
            return false;

        roundTrack.setDice(player.getOverlay().getDice(p1), pr);
        player.getOverlay().setDicePosition(diceRoundtrack, p1);
        return true;
    }

    public boolean moveDiceAlone(Player player, Position p1, Position p2) {

        if (p1 == null || p2 == null)
            return false;

        Dice dice = player.getOverlay().getDice(p1);
        if (dice == null)
            return false;

        if (player.getOverlay().busy(p2))
            return false;

        if (!checkEdgePosTurn(player, p2))
            return false;

        if (!notAdjacentToAny(player.getOverlay(), p2))
            return false;

        if (!checkPlaceRequirements(dice, p2))
            return false;

        player.getOverlay().setDicePosition(dice, p2);
        player.getOverlay().setDicePosition(null, p1);
        return true;
    }
}