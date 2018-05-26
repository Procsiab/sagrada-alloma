package server;

import server.threads.GameManager;
import shared.*;
import shared.abstractsShared.PrivateOC;
import server.abstractsServer.PublicOC;
import server.abstractsServer.Window;

import java.util.ArrayList;

public class Player {
    private MatchManager matchManager = MatchManager.getInstance();
    public String uUID;
    public PrivateOC privateOC;
    public Window window;
    public Overlay overlay = new Overlay();
    public Integer tokens;
    public Integer turno = 0;
    public Integer nPlayer;
    public Integer score = 0;
    public Integer privateTurn = 0; //can be either 1 or 2
    public Position lastPlaced;
    public boolean hasPlacedDice = false;
    public boolean hasUsedTc = false;
    public GameManager game;

    public Player(int i, GameManager gameManager, String uUID) {
        this.uUID = uUID;
        this.nPlayer = i;
        this.game = gameManager;
    }

    public Integer getScore() {
        return score;
    }

    public Integer setScore() {
        ArrayList<PublicOC> publicOCS = game.publicOCs;
        for (PublicOC card :
                publicOCS) {
            score = score + card.use(this);
        }

        int i = 0;
        int j = 0;

        while (i < 4) {
            while (j < 5) {
                Dice dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (dice.color == privateOC.color)
                        score = score + dice.value;
                j++;
            }
            i++;
        }

        score = score + tokens;
        Logger.log("score of player " + this + " is " + score.toString());

        return score;
    }

    public void setPrivateOC(Integer n) {
        privateOC = matchManager.privateOCs.get(n);
    }

    public boolean setWindowFromC(Integer n) {
        if (this.window != null)
            return false;
        this.window = matchManager.windows.get(n);
        return true;
    }

    public boolean setWindow(Integer n) {
        this.window = matchManager.windows.get(n);
        return true;
    }

    public boolean useToolC(Integer i1, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {
        if (this.hasUsedTc)
            return false;
        return game.toolCards.get(i1).use(game, this, p1, p2, p3, p4, pr, i2, i3);
    }

    public boolean placeDice(Integer index, Position position) {
        if (this.hasPlacedDice)
            return false;
        ArrayList<Dice> pool = game.pool;
        if (pool.get(index) == null)
            return false;
        return this.window.setDicePositionFromPool(this, index, position);
    }
}