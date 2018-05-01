package server;

import server.abstracts.Frame;
import server.abstracts.PrivateOC;
import server.abstracts.Window;
import server.networkS.NetworkRmiServer;
import server.threads.GameManager;
import shared.Position;
import shared.SharedClientGame;
import shared.SharedServerPlayer;

public class Player implements SharedServerPlayer {
    private MatchManager matchManager = MatchManager.getInstance();
    public SharedClientGame clientGame;
    public boolean quit = false;
    public PrivateOC privateOC;
    public Window window;
    public Frame frame;
    public Integer tokens;
    public Integer turno;
    public Integer nPlayer;
    public Integer score;
    public GameManager game;

    public Player(int i, GameManager gameManager){
        this.nPlayer = i;
        this.game = gameManager;
        // Export the reference as UnicastRemoteObject
        NetworkRmiServer.getInstance().remotize(this);
    }

    public Integer getScore() {
        return score;
    }

    @Override
    public SharedClientGame getClientGame() {
        return clientGame;
    }

    public void setClientGame(SharedClientGame client) {
        this.clientGame = client;
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
