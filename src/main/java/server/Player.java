package server;

import server.threads.GameManager;
import shared.*;
import shared.abstractsShared.PrivateOC;
import server.abstractsServer.PublicOC;
import server.abstractsServer.Window;

import java.util.ArrayList;

public class Player {
    private MatchManager matchManager = MatchManager.getInstance();
    private String uUID;
    private PrivateOC privateOC;
    private ArrayList<Integer> possibleWindows = new ArrayList<>();
    private Window window;
    private Overlay overlay = new Overlay();
    private Integer tokens;
    private Integer turno = 0;
    private Integer nPlayer;
    private Integer score = 0;
    private Integer privateTurn = 0; //can be either 1 or 2
    private Position lastPlaced;
    private boolean hasPlacedDice = false;
    private boolean hasUsedTc = false;
    private GameManager game;

    public Player(int i, GameManager gameManager, String uUID) {
        this.uUID = uUID;
        this.nPlayer = i;
        this.game = gameManager;
    }

    public ArrayList<Integer> getPossibleWindows() {
        return possibleWindows;
    }

    public void setPossibleWindows(ArrayList<Integer> possibleWindows) {
        this.possibleWindows = possibleWindows;
    }

    public Integer getScore() {
        return score;
    }

    public Integer setScore() {
        ArrayList<PublicOC> publicOCS = game.getPublicOCs();
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
                    if (dice.color == privateOC.getColor())
                        score = score + dice.value;
                j++;
            }
            i++;
        }

        score = score + tokens;
        Logger.log("score of player " + this + " is " + score.toString());

        return score;
    }

    public synchronized boolean placedDice() {
        if (this.hasPlacedDice)
            return false;
        this.hasPlacedDice=true;
        return true;
    }

    public synchronized boolean usedTc() {
        if (this.hasUsedTc)
            return false;
        this.hasUsedTc=true;
        return true;
    }

    public synchronized boolean usedTcAndPlacedDice() {
        if (this.hasUsedTc||this.hasPlacedDice)
            return false;
        this.hasUsedTc=true;
        this.hasPlacedDice=true;
        return true;
    }

    public synchronized void clearUsedTcAndPlacedDice() {
        this.hasUsedTc=false;
        this.hasPlacedDice=false;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTokens() {
        return tokens;
    }

    public void setGame(GameManager game) {
        this.game = game;
    }

    public Integer getTurno() {
        return turno;
    }

    public Integer getnPlayer() {
        return nPlayer;
    }

    public GameManager getGame() {
        return game;
    }

    public Integer getPrivateTurn() {
        return privateTurn;
    }

    public MatchManager getMatchManager() {
        return matchManager;
    }

    public Overlay getOverlay() {
        return overlay;
    }

    public Position getLastPlaced() {
        return lastPlaced;
    }

    public PrivateOC getPrivateOC() {
        return privateOC;
    }

    public String getuUID() {
        return uUID;
    }

    public void setLastPlaced(Position lastPlaced) {
        this.lastPlaced = lastPlaced;
    }

    public void setMatchManager(MatchManager matchManager) {
        this.matchManager = matchManager;
    }

    public void setnPlayer(Integer nPlayer) {
        this.nPlayer = nPlayer;
    }

    public void setOverlay(Overlay overlay) {
        this.overlay = overlay;
    }

    public void setPrivateOC(PrivateOC privateOC) {
        this.privateOC = privateOC;
    }

    public void setPrivateTurn(Integer privateTurn) {
        this.privateTurn = privateTurn;
    }

    public void setTokens(Integer tokens) {
        this.tokens = tokens;
    }

    public void incrementTurn() {
        this.turno++;
        if(this.privateTurn ==1)
            this.privateTurn = 2;
        else
            this.privateTurn =1;
    }

    public void setuUID(String uUID) {
        this.uUID = uUID;
    }

    public Window getWindow() {
        return window;
    }

    public void setPrivateOC(Integer n) {
        privateOC = matchManager.getPrivateOCs().get(n);
    }

    public boolean setWindowFromC(Integer n) {
        if (this.window != null)
            return false;
        if(!this.possibleWindows.contains(n))
            return false;
        this.window = matchManager.getWindows().get(n);
        return true;
    }

    public boolean setWindow(Integer n) {
        this.window = matchManager.getWindows().get(n);
        return true;
    }

    public void setTokens() {
        this.tokens = this.window.getTokens();
    }

    public boolean useToolC(Integer i1, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {
        if (i1 == null || i1 < 0 || i1 > 2)
            return false;
        return game.getToolCards().get(i1).use(game, i1, this, p1, p2, p3, p4, pr, i2, i3);
    }

    public boolean placeDice(Integer index, Position position) {
        if (this.placedDice())
            return false;
        ArrayList<Dice> pool = game.getPool();
        if (pool.get(index) == null)
            return false;
        return this.window.setDiceFromPool(this, index, position);
    }
}