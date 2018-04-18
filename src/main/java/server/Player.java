package server;

import server.abstracts.Frame;
import server.abstracts.PrivateOC;
import server.abstracts.ToolC;
import server.abstracts.Window;
import server.threads.GameManager;

public class Player {
    private MatchManager matchManager = MatchManager.getInstance();
    public boolean quit = false;
    public PrivateOC privateOC;
    public Window window;
    public Frame frame;
    public Integer tokens;
    public Integer turno;
    public Integer nPlayer;
    public GameManager game;

    public Player(int i, GameManager gameManager){
        this.nPlayer = i;
        this.game = gameManager;
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

    public boolean useToolC(ToolC toolC){
        return toolC.use(this);
    }
    public void setTokens() {
        this.tokens = this.window.tokens;
    }
}
