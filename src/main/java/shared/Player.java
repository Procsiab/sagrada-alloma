package shared;

import server.MatchManager;
import shared.Dice;
import shared.abstracts.Frame;
import shared.abstracts.PrivateOC;
import shared.abstracts.Window;
import shared.GameManager;
import shared.Position;

import java.io.Serializable;

public class Player implements Serializable {
    private MatchManager matchManager = MatchManager.getInstance();
    public String uUID;
    public PrivateOC privateOC;
    public Window window;
    public Frame frame;
    public Integer tokens;
    public Integer turno;
    public Integer nPlayer;
    public Integer score;
    public GameManager game;

    public Player(int i, GameManager gameManager, String uUID){
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

    public void setWindow(Integer n) {
        this.window = matchManager.windows.get(n);
    }

    public void setFrame(Integer n) {
        this.frame = matchManager.frames.get(n);
        this.frame.window = this.window;
    }

    public boolean placeDice(Dice dice, Position position){
        return this.frame.setDicePositions(dice, position, this);
    }

    public void setTokens() {
        this.tokens = this.window.tokens;
    }
}
