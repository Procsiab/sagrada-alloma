package server.abstractsServer;

import server.Player;
import server.SReferences;
import shared.*;

import java.io.Serializable;

public abstract class Window implements Serializable {

    public Cell[][] cells;
    public Integer tokens = 0;
    private Dice[][] dicePositions;
    public Integer color;
    public Position lastPlacedPosition;
    public String name;


    public Cell[][] getCells() {
        return cells;
    }

    public Integer getTokens() {
        return tokens;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void setTokens(Integer tokens) {
        this.tokens = tokens;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNome() {
        return name;
    }

    public boolean checkEdgePosTurn(Player player, Position position) {
        if (player.turno != 1)
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

    public boolean checkPlaceColorRequirements(Dice dice, Position position) {
        if (this.getCells()[position.getRow()][position.getColumn()] == null)
            return true;
        if (dice.getColor() != this.getCells()[position.getRow()][position.getColumn()].getColor()) {
            return false;
        }
        return true;
    }

    public boolean checkPlaceValueRequirements(Dice dice, Position position) {
        if (this.getCells()[position.getRow()][position.getColumn()] == null)
            return true;
        if (dice.getValue() != this.getCells()[position.getRow()][position.getColumn()].getValue()) {
            return false;
        }
        return true;
    }

    public boolean checkPlaceRequirements(Dice dice, Position position) {
        return checkPlaceValueRequirements(dice, position) && checkPlaceColorRequirements(dice, position);
    }

    public boolean setDicePositionFromPool(Player player, Integer index, Position position) {

        if (index == null || position == null)
            return false;

        Dice dice = player.game.pool.get(index);
        if (dice == null)
            return false;

        if (player.overlay.busy(position))
            return false;

        if (checkDicePosition(player, dice, position)) {
            player.overlay.setDicePosition(dice, position);
            SReferences.getGameRefEnhanced(player.uUID).pool.set(index, null);
            return true;
        }
        return false;
    }

    public boolean moveDicePosition(Player player, Position p1, Position p2) {

        if (p1 == null || p2 == null)
            return false;

        Dice dice = player.overlay.getDice(p1);
        if (dice == null)
            return false;

        if (player.overlay.busy(p2))
            return false;

        if (checkDicePosition(player, dice, p2)) {
            player.overlay.setDicePosition(dice, p2);
            player.overlay.setDicePosition(null, p1);
            return true;
        }
        return false;
    }

    public boolean moveDicePosition(Player player, Position p1, Position p2, Position p3, Position p4) {

        if (p1 == null || p2 == null || p3 == null || p4 == null)
            return false;

        Dice dice1 = player.overlay.getDice(p1);
        Dice dice2 = player.overlay.getDice(p2);
        if (dice1 == null || dice2 == null)
            return false;

        if (player.overlay.busy(p2))
            return false;
        if (player.overlay.busy(p4))
            return false;

        if (checkDicePosition(player, dice1, p2)) {
            player.overlay.setDicePosition(dice1, p2);
            player.overlay.setDicePosition(null, p1);
            if (checkDicePosition(player, dice2, p4)) {
                player.overlay.setDicePosition(dice2, p4);
                player.overlay.setDicePosition(null, p3);
                return true;
            }
        }
        player.overlay.setDicePosition(dice1, p1);
        player.overlay.setDicePosition(null, p2);
        player.overlay.setDicePosition(dice2, p3);
        player.overlay.setDicePosition(null, p4);
        return false;
    }

    public boolean checkDicePosition(Player player, Dice dice, Position position) {
        Overlay overlay = player.overlay;


        if (!checkEdgePosTurn(player, position))
            return false;


        if (!checkAdjDicesFull(overlay, position, dice))
            return false;

        if (!checkPlaceRequirements(dice, position))
            return false;
        return true;
    }

    public boolean moveDicePositionNoShade(Player player, Position p1, Position p2) {

        if (p1 == null || p2 == null)
            return false;

        Dice dice = player.overlay.getDice(p1);
        if (dice == null)
            return false;

        if (player.overlay.busy(p2))
            return false;

        if (checkDicePosition(player, dice, p2)) {
            player.overlay.setDicePosition(dice, p2);
            player.overlay.setDicePosition(null, p1);
            return true;
        }
        return false;
    }

    public boolean checkDicePositionNoShade(Player player, Dice dice, Position position) {
        Overlay overlay = player.overlay;


        if (!checkEdgePosTurn(player, position))
            return false;


        if (!checkAdjDicesFull(overlay, position, dice))
            return false;

        if (!checkPlaceColorRequirements(dice, position))
            return false;
        return true;
    }

    public boolean moveDicePositionNoColor(Player player, Position p1, Position p2) {

        if (p1 == null || p2 == null)
            return false;

        Dice dice = player.overlay.getDice(p1);
        if (dice == null)
            return false;

        if (player.overlay.busy(p2))
            return false;

        if (checkDicePosition(player, dice, p2)) {
            player.overlay.setDicePosition(dice, p2);
            player.overlay.setDicePosition(null, p1);
            return true;
        }
        return false;
    }

    public boolean checkDicePositionNoColor(Player player, Dice dice, Position position) {
        Overlay overlay = player.overlay;


        if (!checkEdgePosTurn(player, position))
            return false;


        if (!checkAdjDicesFull(overlay, position, dice))
            return false;

        if (!checkPlaceValueRequirements(dice, position))
            return false;
        return true;
    }
}