package server;

import server.threads.GameManager;
import shared.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Window implements Serializable {

    private final Cell[][] matrices;
    private final Integer tokens;
    private final String name;

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

    private Boolean checkSideBySide(Overlay overlay, Position position, Dice dice) {
        Dice adj;
        Integer row = position.getRow();
        Integer col = position.getColumn();

        adj = overlay.getDice(new Position(row, col - 1));
        if (adj != null)
            if (dice.isCloseTo(adj))
                return false;
        adj = overlay.getDice(new Position(row - 1, col));
        if (adj != null)
            if (dice.isCloseTo(adj))
                return false;
        adj = overlay.getDice(new Position(row, col + 1));
        if (adj != null)
            if (dice.isCloseTo(adj))
                return false;
        adj = overlay.getDice(new Position(row + 1, col));
        if (adj != null)
            if (dice.isCloseTo(adj))
                return false;
        return true;
    }

    private boolean checkEdgePosTurn(Player player, Position position) {
        if (position.getRow().equals(0) || position.getRow().equals(3) || position.getColumn().equals(0) || position.getColumn().equals(4))
            return true;
        return false;
    }

    private boolean checkAdjDicesFull(Overlay overlay, Position position, Dice dice) {
        return checkSideBySide(overlay, position, dice) && !CheckNotAdjacentToAny(overlay, position);
    }

    private boolean CheckNotAdjacentToAny(Overlay overlay, Position position1) {
        if (overlay.busy(new Position(position1.getRow() - 1, position1.getColumn() - 1)))
            return false;
        if (overlay.busy(new Position(position1.getRow(), position1.getColumn() - 1)))
            return false;
        if (overlay.busy(new Position(position1.getRow() + 1, position1.getColumn() - 1)))
            return false;
        if (overlay.busy(new Position(position1.getRow() - 1, position1.getColumn())))
            return false;
        if (overlay.busy(new Position(position1.getRow() + 1, position1.getColumn())))
            return false;
        if (overlay.busy(new Position(position1.getRow() - 1, position1.getColumn() + 1)))
            return false;
        if (overlay.busy(new Position(position1.getRow(), position1.getColumn() + 1)))
            return false;
        if (overlay.busy(new Position(position1.getRow() + 1, position1.getColumn() + 1)))
            return false;

        return true;
    }

    private boolean checkPlaceColorRequirements(Dice dice, Position position) {
        if (getCell(position).getColor() == null)
            return true;
        return dice.getColor().equals(getCell(position).getColor());
    }

    private boolean checkPlaceValueRequirements(Dice dice, Position position) {
        if (getCell(position).getValue() == null)
            return true;
        return dice.getValue().equals(getCell(position).getValue());
    }

    private boolean checkPlaceRequirements(Dice dice, Position position) {
        return checkPlaceValueRequirements(dice, position) && checkPlaceColorRequirements(dice, position);
    }

    public boolean setDiceFromPool(Player player, Integer index, Position position) {
        ArrayList<Dice> pool = player.getGame().getPool();
        if (index == null || position == null ||
                position.getRow() < 0 || position.getRow() > 3
                || position.getColumn() < 0 || position.getColumn() > 4 ||
                index >= pool.size() || index < 0) {
            System.out.println("(temporary print) error code 1 ");
            return false;
        }

        Dice dice = pool.get(index);
        if (dice == null || player.getOverlay().busy(position)
                || !checkDice(player, dice, position)) {
            if (dice == null)
                System.out.println("(temporary print) error code 2a ");
            if (player.getOverlay().busy(position))
                System.out.println("(temporary print) error code 2b ");
            if (!checkDice(player, dice, position))
                System.out.println("(temporary print) error code 2c ");
            return false;
        }

        player.getOverlay().setDicePosition(dice, position);
        pool.set(index, null);
        return true;
    }

    public boolean moveDice(Player player, Position p1, Position p2) {
        Overlay overlay = player.getOverlay();

        if (p1 == null || p2 == null)
            return false;
        Dice dice1 = overlay.getDice(p1);

        if (overlay.busy(p2) || !overlay.busy(p1))
            return false;

        if (checkDice(player, dice1, p2)) {
            overlay.setDicePosition(dice1, p2);
            overlay.setDicePosition(null, p1);
            return true;
        }
        overlay.setDicePosition(dice1, p1);
        overlay.setDicePosition(null, p2);
        return false;
    }

    public boolean moveDice(Player player, Position p1, Position p2, Position p3, Position p4) {
        Overlay overlay = player.getOverlay();
        if (p1 == null || p2 == null || p3 == null || p4 == null)
            return false;

        Dice dice1 = overlay.getDice(p1);
        Dice dice2 = overlay.getDice(p3);

        if (overlay.busy(p2) || overlay.busy(p4) || !overlay.busy(p1) || !overlay.busy(p3))
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

    private boolean checkDice(Player player, Dice dice, Position position) {
        Overlay overlay = player.getOverlay();
        if (player.getLastPlacedFromPool().equals(new Position(-1, -1))) {
            if (!checkEdgePosTurn(player, position)) {
                System.out.println("(temporary print) error code 3a");
                return false;
            }
        } else {
            if (!checkAdjDicesFull(overlay, position, dice)) {
                System.out.println("(temporary print) error code 3b");
                return false;
            }
        }
        if (!checkPlaceRequirements(dice, position)) {
            System.out.println("(temporary print) error code 3c");
            return false;
        }
        return true;
    }

    public boolean moveDiceNoShade(Player player, Position p1, Position p2) {
        Overlay overlay = player.getOverlay();
        if (p1 == null || p2 == null)
            return false;

        Dice dice = overlay.getDice(p1);
        if (dice == null || overlay.busy(p2))
            return false;

        if (checkDiceNoShade(player, dice, p2)) {
            overlay.setDicePosition(dice, p2);
            overlay.setDicePosition(null, p1);
            return true;
        }
        return false;
    }

    private boolean checkDiceNoShade(Player player, Dice dice, Position position) {
        Overlay overlay = player.getOverlay();
        if (!player.getLastPlacedFromPool().equals(new Position(-1, -1))) {
            if (!checkEdgePosTurn(player, position))
                return false;
        } else {
            if (!checkAdjDicesFull(overlay, position, dice))
                return false;
        }
        if (!checkPlaceColorRequirements(dice, position))
            return false;
        return true;
    }

    public boolean moveDiceNoColor(Player player, Position p1, Position p2) {

        if (p1 == null || p2 == null)
            return false;

        Overlay overlay = player.getOverlay();
        Dice dice = overlay.getDice(p1);
        if (dice == null || overlay.busy(p2))
            return false;

        if (checkDiceNoColor(player, dice, p2)) {
            overlay.setDicePosition(dice, p2);
            overlay.setDicePosition(null, p1);
            return true;
        }
        return false;
    }

    private boolean checkDiceNoColor(Player player, Dice dice, Position position) {
        Overlay overlay = player.getOverlay();
        if (!player.getLastPlacedFromPool().equals(new Position(-1, -1))) {
            if (!checkEdgePosTurn(player, position))
                return false;
        } else {
            if (!checkAdjDicesFull(overlay, position, dice))
                return false;
        }
        if (!checkPlaceValueRequirements(dice, position))
            return false;
        return true;
    }

    public boolean moveDiceWindowRoundtrack(GameManager gameManager, Player player, Integer pos, Position p1, PositionR pr) {

        RoundTrack roundTrack = gameManager.getRoundTrack();
        if (!roundTrack.busy(pr) || player.getOverlay().busy(p1) ||
                (pos < 0 || pos > gameManager.getPool().size() - 1) ||
                (gameManager.getPool().get(pos) == null))
            return false;

        Dice diceRoundtrack = roundTrack.getDice(pr);

        roundTrack.setDice(gameManager.getPool().get(pos), pr);

        if (!checkDice(player, diceRoundtrack, p1)) {
            gameManager.getPool().set(pos, diceRoundtrack);
            return false;
        }
        gameManager.getPool().set(pos, null);
        player.getOverlay().setDicePosition(diceRoundtrack, p1);
        return true;
    }

    public boolean moveDiceAlone(Player player, Position p1, Position p2) {
        Dice dice = player.getOverlay().getDice(p1);
        if (p1 == null || p2 == null || dice == null ||
                player.getOverlay().busy(p2) ||
                !CheckNotAdjacentToAny(player.getOverlay(), p2) ||
                !checkPlaceRequirements(dice, p2))
            return false;

        player.getOverlay().setDicePosition(dice, p2);
        player.getOverlay().setDicePosition(null, p1);
        return true;
    }
}