package server;

import server.threads.GameManager;
import shared.Dice;
import shared.Overlay;
import shared.Position;
import server.abstracts.PrivateOC;
import server.abstracts.Window;

import java.io.Serializable;

public class Player{
    private transient MatchManager matchManager = MatchManager.getInstance();
    public String uUID;
    public PrivateOC privateOC;
    public Window window;
    public Overlay overlay;
    public Integer tokens;
    public Integer turno = 0;
    public Integer nPlayer;
    public Integer score;
    public Integer privateTurn; //can be either 1 or 2
    public Position lastPlaced;
    public GameManager game;

    public Player(int i, GameManager gameManager, String uUID) {
        this.uUID = uUID;
        this.nPlayer = i;
        this.game = gameManager;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setPrivateOC(Integer n) {
        privateOC = matchManager.privateOCs.get(n);
    }

    public boolean setWindow(Integer n) {
        if(this.window != null)
            return false;
        this.window = matchManager.windows.get(n);
        return true;
    }

    public boolean placeDice(Dice dice, Position position) {
        return this.window.setDicePosition(this, dice, position);
    }

    public void setTokens() {
        this.tokens = this.window.tokens;
    }
}