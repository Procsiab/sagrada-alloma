package server;

import server.executables.Tool;
import server.threads.GameManager;
import shared.*;

import java.util.ArrayList;

public class Player {
    private MatchManager matchManager = MatchManager.getInstance();
    private String uUID;
    private String nickName;
    private Character privateO;
    private ArrayList<Integer> possibleWindows;
    private Window window;
    private Overlay overlay = new Overlay();
    private Integer tokens = 0;
    private Integer turno = 0;
    private Integer score = 0;
    private Integer privateTurn = 0; //can be either 1 or 2
    private Position lastPlacedFromPool = new Position(-1, -1);
    private boolean hasPlacedDice = false;
    private boolean hasUsedTc = false;
    private GameManager game;

    public Player(GameManager gameManager, String uUID) {
        this.uUID = uUID;
        this.game = gameManager;
        this.possibleWindows = new ArrayList<>();
        this.nickName = SReferences.getNickNameRef(uUID);
    }

    public synchronized Boolean useTool(String uUID, Integer i1, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {
        Boolean esito = false;
        Integer nCard;

        if (!(i1 == null || i1 < 0 || i1 > 2)) {
            nCard = game.getToolCards().get(i1);
            switch (nCard + 1) {
                case 1:
                    esito = Tool.use1(game, i1, SReferences.getPlayerRef(uUID), p1, i2, i3);
                    break;
                case 2:
                    esito = Tool.use2(i1, SReferences.getPlayerRef(uUID), p1, p2);
                    break;
                case 3:
                    esito = Tool.use3(i1, SReferences.getPlayerRef(uUID), p1, p2);
                    break;
                case 4:
                    esito = Tool.use4(i1, SReferences.getPlayerRef(uUID), p1, p2, p3, p4);
                    break;
                case 5:
                    esito = Tool.use5(game, i1, SReferences.getPlayerRef(uUID), p1, pr, i2);
                    break;
                case 6:
                    esito = Tool.use6(game, i1, SReferences.getPlayerRef(uUID), p1, i2);
                    break;
                case 7:
                    esito = Tool.use7(game, i1, SReferences.getPlayerRef(uUID));
                    break;
                case 8:
                    esito = Tool.use8(game, i1, SReferences.getPlayerRef(uUID), p1, i2);
                    break;
                case 9:
                    esito = Tool.use9(i1, SReferences.getPlayerRef(uUID), p1, p2, i2);
                    break;
                case 10:
                    esito = Tool.use10(game, i1, SReferences.getPlayerRef(uUID), p1, i2);
                    break;
                case 11:
                    esito = Tool.use11(game, i1, SReferences.getPlayerRef(uUID), p1, i2, i3);
                    break;
                case 12:
                    esito = Tool.use12(game, i1, SReferences.getPlayerRef(uUID), p1, p2, p3, p4, pr);
                    break;
            }
            if (esito) {
                Logger.log(game + " player " + uUID + " effectively used " +
                        game.revealToolCard(nCard));
                return true;
            }
        }
        Logger.log(game + " player " + uUID + " attempt of unauthorized usage of ToolCard");
        return false;

    }

    public void setPossibleWindows(ArrayList<Integer> possibleWindows) {
        this.possibleWindows.addAll(possibleWindows);
    }

    public String getNickName() {
        return nickName;
    }

    public Integer getScore() {

        score = game.usePublicO(this.overlay);

        int i = 0;
        int j = 0;

        while (i < 4) {
            while (j < 5) {
                Dice dice = overlay.getDice(new Position(i, j));
                if (dice != null)
                    if (dice.getColor().equals(privateO))
                        score = score + dice.getValue();
                j++;
            }
            j = 0;
            i++;
        }
        score = score + tokens;
        Logger.log("Player: " + uUID + " total score is " + score);

        return score;
    }

    public Integer getComputatedScore() {
        return this.score;
    }

    public boolean placedDice() {
        if (this.hasPlacedDice)
            return true;
        this.hasPlacedDice = true;
        return false;
    }

    public boolean usedTc() {
        if (this.hasUsedTc)
            return true;
        this.hasUsedTc = true;
        return false;
    }

    public boolean usedTcAndPlacedDice() {
        if (this.hasUsedTc || this.hasPlacedDice)
            return true;
        this.hasUsedTc = true;
        this.hasPlacedDice = true;
        return false;
    }

    public void clearUsedTcAndPlacedDice() {
        this.hasUsedTc = false;
        this.hasPlacedDice = false;
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

    public GameManager getGame() {
        return game;
    }

    public Integer getPrivateTurn() {
        return privateTurn;
    }

    public Overlay getOverlay() {
        return overlay;
    }

    public Character getPrivateO() {
        return privateO;
    }

    public Position getLastPlacedFromPool() {
        return lastPlacedFromPool;
    }

    public String getuUID() {
        return uUID;
    }

    public void setTokens(Integer tokens) {
        this.tokens = tokens;
    }

    public void incrementTurn() {
        this.turno++;
        if (this.privateTurn.equals(1))
            this.privateTurn = 2;
        else
            this.privateTurn = 1;
    }

    public synchronized Window getWindow() {
        return window;
    }

    public void setPrivateOC(Character ch) {
        this.privateO = ch;
        Logger.log("Player: " + uUID + ", Private Objective card " +
                "assigned with color " + ch);
    }

    public synchronized boolean setWindowFromC(Integer n) {
        if (this.window != null) {
            Logger.log("Player: " + uUID + " Server already assigned Window for this player");
            return false;
        }
        if (!this.possibleWindows.contains(n)) {
            Logger.log("Player: " + uUID + " Attempt to set improper Window");
            return false;
        }
        this.window = matchManager.getWindows().get(n);
        setTokens();
        Logger.log("Player: " + uUID + " choose " + game.revealWindow(n) + ". It has: " + window.getTokens() + " tokens");
        return true;
    }

    public synchronized void setWindow(Integer n) {
        this.window = MatchManager.getWindows().get(n);
        setTokens();
        Logger.log(game + " player " + uUID + " server assigned Window n° " + n + ". It has " + window.getTokens() +
                " tokens. Will be forced start client-side");
    }

    public void setTokens() {
        this.tokens = this.window.getTokens();
    }

    public synchronized boolean placeDice(Integer index, Position position) {
        System.out.println("(temporary print) " + game.revealWindow(MatchManager.getWindows().indexOf(this.window)));
        Dice dice;
        if (!this.placedDice()) {
            ArrayList<Dice> pool = game.getPool();
            if (index >= pool.size()||index<0||pool.get(index)==null) {
                System.out.println("(temporary print) index out of bounds");
                return false;
            }
            dice = pool.get(index);
            if (this.window.setDiceFromPool(this, index, position)) {
                this.lastPlacedFromPool = position;
                Logger.log(game + " player " + uUID + " effectively placed dice " +
                        dice + " in position " + position);
                return true;
            }
        }
        Logger.log(game + " player " + uUID + " attempt of unauthorized placement of dice " +
                "in position " + position);
        return false;
    }
}