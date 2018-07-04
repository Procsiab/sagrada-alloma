package server;

import server.threads.GameManager;
import shared.*;

import java.io.Serializable;

public class Window {

    private final Cell[][] matrices;
    private final Integer tokens;
    private final String name;

    /**
     * build a new window, where
     * @param matrices is the matrix of cells
     * @param name is the name of the window
     * @param tokens is the number of tokens the card give the player
     */
    public Window(Cell[][] matrices, String name, Integer tokens) {
        this.name = name;
        this.matrices = matrices;
        this.tokens = tokens;
    }

    public Cell[][] getMatrices() {
        return matrices;
    }

    public Integer getTokens() {
        return tokens;
    }

    public Cell getCell(Position pos) {
        if (pos.getRow() > 3 || pos.getRow() < 0 || pos.getColumn() > 4 || pos.getColumn() < 0)
            return null;
        return matrices[pos.getRow()][pos.getColumn()];
    }

    public String getName() {
        return name;
    }

    /**
     * check whether
     * @param player hasn't placed dices yet.
     * @return gives true if this is tha case, false otherwise
     */
    private Boolean checkFirstTurn(Player player) {
        return player.getLastPlacedFromPool().equals(new Position(-1, -1));
    }

    /**
     * check wheter in the
     * @param overlay in the
     * @param position there is a diagonally adjacent dice which has
     *                the same color or shade of the following
     * @param dice
     */
    private Boolean checkSideBySide(Overlay overlay, Position position, Dice dice) {
        Position positionAdj;
        Dice adj;
        Integer row = position.getRow();
        Integer col = position.getColumn();
        Boolean esito = true;

        positionAdj = new Position(row, col - 1);
        if (overlay.validateBusy(positionAdj)) {
            adj = overlay.getDice(positionAdj);
            if (dice.isCloseTo(adj))
                esito = false;
        }
        positionAdj = new Position(row - 1, col);
        if (overlay.validateBusy(positionAdj)) {
            adj = overlay.getDice(positionAdj);
            if (dice.isCloseTo(adj))
                esito = false;
        }
        positionAdj = new Position(row, col + 1);
        if (overlay.validateBusy(positionAdj)) {
            adj = overlay.getDice(positionAdj);
            if (dice.isCloseTo(adj))
                esito = false;
        }
        positionAdj = new Position(row + 1, col);
        if (overlay.validateBusy(positionAdj)) {
            adj = overlay.getDice(positionAdj);
            if (dice.isCloseTo(adj))
                esito = false;
        }
        return esito;
    }

    /**
     * check if the
     * @param position is on the edge of the overlay
     */
    private Boolean checkEdgePosTurn(Position position) {
        return position.getRow().equals(0) || position.getRow().equals(3) || position.getColumn().equals(0) || position.getColumn().equals(4);
    }

    private Boolean checkAdjDicesFull(Overlay overlay, Position position, Dice dice) {
        return checkSideBySide(overlay, position, dice) && checkAdjacentToAny(overlay, position);
    }

    /**
     * check if in the
     * @param overlay, on
     * @param position1 there is at least an adjacent dice
     */
    private Boolean checkAdjacentToAny(Overlay overlay, Position position1) {
        return (overlay.validateBusy(new Position(position1.getRow() - 1, position1.getColumn() - 1)) || (overlay.validateBusy(new Position(position1.getRow(), position1.getColumn() - 1))) || (overlay.validateBusy(new Position(position1.getRow() + 1, position1.getColumn() - 1))) || (overlay.validateBusy(new Position(position1.getRow() - 1, position1.getColumn()))) || (overlay.validateBusy(new Position(position1.getRow() + 1, position1.getColumn()))) || (overlay.validateBusy(new Position(position1.getRow() - 1, position1.getColumn() + 1))) || (overlay.validateBusy(new Position(position1.getRow(), position1.getColumn() + 1))) || (overlay.validateBusy(new Position(position1.getRow() + 1, position1.getColumn() + 1))));
    }

    /**
     * check if in the
     * @param overlay on
     * @param position1 there are no adjacent dices
     */
    private Boolean checkFreeSpaceAround(Overlay overlay, Position position1) {
        return overlay.validateFree(new Position(position1.getRow() - 1, position1.getColumn() - 1)) && (overlay.validateFree(new Position(position1.getRow(), position1.getColumn() - 1))) && (overlay.validateFree(new Position(position1.getRow() + 1, position1.getColumn() - 1))) && (overlay.validateFree(new Position(position1.getRow() - 1, position1.getColumn()))) && (overlay.validateFree(new Position(position1.getRow() + 1, position1.getColumn()))) && (overlay.validateFree(new Position(position1.getRow() - 1, position1.getColumn() + 1))) && (overlay.validateFree(new Position(position1.getRow(), position1.getColumn() + 1))) && (overlay.validateFree(new Position(position1.getRow() + 1, position1.getColumn() + 1)));
    }

    /**
     * check if the
     * @param dice in
     * @param position match the color of the window
     */
    private Boolean checkPlaceColorRequirements(Dice dice, Position position) {
        if (getCell(position).getColor() == null)
            return true;
        return dice.getColor().equals(getCell(position).getColor());
    }
    /**
     * check if the
     * @param dice in
     * @param position match the shade of the window
     */
    private Boolean checkPlaceValueRequirements(Dice dice, Position position) {
        if (getCell(position).getValue() == null)
            return true;
        return dice.getValue().equals(getCell(position).getValue());
    }

    private Boolean checkPlaceRequirements(Dice dice, Position position) {
        return checkPlaceValueRequirements(dice, position) && checkPlaceColorRequirements(dice, position);
    }

    /**
     * after the appropriate checks,
     * @param player takes a dice in position
     * @param index among the dices in the pool and place it on
     * @param position in the overlay
     * @return if this happened correctly
     */
    public Boolean placeDiceFromPool(Player player, Integer index, Position position) {

        Pool pool = player.getPool();
        Overlay overlay = player.getOverlay();
        Dice dice = pool.getDice(index);
        if (!checkDice(player, dice, position)) {
            return false;
        }
        overlay.setDicePosition(dice, position);
        pool.setDice(index, null);
        return true;
    }

    /**
     * @param player move its dice (if allowed) from
     * @param p1 to
     * @param p2 in the overlay
     */
    public Boolean moveDice(Player player, Position p1, Position p2) {

        Overlay overlay = player.getOverlay();
        Dice dice1 = overlay.getDice(p1);
        overlay.setDicePosition(null, p1);

        if (checkDice(player, dice1, p2)) {
            overlay.setDicePosition(dice1, p2);
            return true;
        }
        overlay.setDicePosition(dice1, p1);
        return false;
    }
    /**
     * @param player move its dices (if allowed) from
     * @param p1 to
     * @param p2 in the overlay
     */
    public Boolean moveDice(Player player, Position p1, Position p2, Position p3, Position p4) {
        Overlay overlay = player.getOverlay();

        Dice dice1 = overlay.getDice(p1);
        Dice dice2 = overlay.getDice(p3);
        overlay.setDicePosition(null, p1);

        if (checkDice(player, dice1, p2)) {
            overlay.setDicePosition(dice1, p2);
            overlay.setDicePosition(null, p3);
            if (checkDice(player, dice2, p4)) {
                overlay.setDicePosition(dice2, p4);
                return true;
            }
            overlay.setDicePosition(dice2, p3);
        }
        overlay.setDicePosition(dice1, p1);
        return false;
    }

    private Boolean checkDice(Player player, Dice dice, Position position) {
        Overlay overlay = player.getOverlay();
        return (!checkFirstTurn(player) && checkAdjDicesFull(overlay, position, dice) || checkFirstTurn(player) && checkEdgePosTurn(position)) && checkPlaceRequirements(dice, position);
    }

    public Boolean moveDiceNoShade(Player player, Position p1, Position p2) {

        Overlay overlay = player.getOverlay();
        Dice dice = overlay.getDice(p1);
        overlay.setDicePosition(null, p1);
        if (checkDiceNoShade(player, dice, p2)) {
            overlay.setDicePosition(dice, p2);
            return true;
        }
        overlay.setDicePosition(dice, p1);
        return false;
    }

    private Boolean checkDiceNoShade(Player player, Dice dice, Position position) {
        Overlay overlay = player.getOverlay();
        return (!checkFirstTurn(player) && checkAdjDicesFull(overlay, position, dice) || checkFirstTurn(player) && checkEdgePosTurn(position)) && checkPlaceColorRequirements(dice, position);


    }

    public Boolean moveDiceNoColor(Player player, Position p1, Position p2) {

        Overlay overlay = player.getOverlay();
        Dice dice = overlay.getDice(p1);
        overlay.setDicePosition(null, p1);
        if (checkDiceNoColor(player, dice, p2)) {
            overlay.setDicePosition(dice, p2);
            return true;
        }
        overlay.setDicePosition(dice, p1);
        return false;
    }

    private Boolean checkDiceNoColor(Player player, Dice dice, Position position) {
        Overlay overlay = player.getOverlay();
        return (!checkFirstTurn(player) && checkAdjDicesFull(overlay, position, dice) || checkFirstTurn(player) && checkEdgePosTurn(position)) && checkPlaceValueRequirements(dice, position);
    }

    public Boolean moveDiceWindowRoundtrack(Player player, Integer pos, Position p1, PositionR pr) {

        GameManager gameManager = player.getGame();
        RoundTrack roundTrack = gameManager.getRoundTrack();
        Dice diceRoundTrack = roundTrack.getDice(pr);
        Pool pool = gameManager.getPool();
        Overlay overlay = player.getOverlay();

        roundTrack.setDice(pool.getDice(pos), pr);

        if (!checkDice(player, diceRoundTrack, p1)) {
            pool.setDice(pos, diceRoundTrack);
            return false;
        }
        pool.setDice(pos, null);
        overlay.setDicePosition(diceRoundTrack, p1);
        return true;
    }

    public Boolean placeDiceAlone(Player player, Integer index, Position position) {

        GameManager gameManager = player.getGame();
        Pool pool = gameManager.getPool();
        Overlay overlay = player.getOverlay();

        Dice dice = pool.getDice(index);
        if ((!checkFirstTurn(player) && checkFreeSpaceAround(overlay, position) || checkFirstTurn(player) && checkEdgePosTurn(position)) && checkPlaceRequirements(dice, position)) {
            player.getOverlay().setDicePosition(dice, position);
            pool.setDice(index, null);
            return true;
        }
        return false;
    }
}