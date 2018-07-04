package server;

import server.executables.Tool;
import server.threads.GameManager;
import shared.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Player {
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
    private Position lastPlacedFromPool;
    private boolean hasPlacedDice = false;
    private boolean hasUsedTc = false;
    private Pool pool;
    private GameManager game;

    public Player(GameManager gameManager, String uUID) {
        this.uUID = uUID;
        this.game = gameManager;
        this.lastPlacedFromPool = new Position(-1, -1);
        this.pool = gameManager.getPool();
        this.nickName = SReferences.getNickNameRef(uUID);
    }

    /**
     * sort players according to score. If they have the same score it sort by it nickname
     */
    public static final Comparator<Player> cmp = new Comparator<Player>() {
        @Override
        public int compare(Player o1, Player o2) {
            Integer r1 = o2.score.compareTo(o1.score);
            if (r1 == 0) {
                return o2.nickName.compareTo(o1.nickName);
            }
            return r1;
        }
    };

    /**
     * this is where the card to use is chosen among the three allowed
     * @param i1 specifies the position of the card among the three allowed
     * @see GameManager#toolCards
     * the parameters can't be be generally described since they take different meaning according to which card is requested
     * @see Tool
     */
    public synchronized Boolean useTool(Integer i1, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {
        Boolean esito = false;
        Integer nCard;

        if (!(i1 == null || i1 < 0 || i1 > 2)) {
            nCard = game.getToolCards().get(i1);
            switch (nCard + 1) {
                case 1:
                    esito = Tool.use1(i1, this, p1, i2, i3);
                    if (esito)
                        usedTcAndPlacedDiceA();
                    break;
                case 2:
                    esito = Tool.use2(i1, this, p1, p2);
                    if (esito)
                        usedTcA();
                    break;
                case 3:
                    esito = Tool.use3(i1, this, p1, p2);
                    if (esito)
                        usedTcA();
                    break;
                case 4:
                    esito = Tool.use4(i1, this, p1, p2, p3, p4);
                    if (esito)
                        usedTcA();
                    break;
                case 5:
                    esito = Tool.use5(i1, this, p1, pr, i2);
                    if (esito)
                        usedTcAndPlacedDiceA();
                    break;
                case 6:
                    esito = Tool.use6(i1, this, p1, i2);
                    if (esito)
                        usedTcAndPlacedDiceA();
                    break;
                case 7:
                    esito = Tool.use7(i1, this);
                    if (esito)
                        usedTcA();
                    break;
                case 8:
                    esito = Tool.use8(i1, this, p1, i2);
                    if (esito)
                        usedTcA();
                    break;
                case 9:
                    esito = Tool.use9(i1, this, p1, i2);
                    if (esito)
                        usedTcAndPlacedDiceA();
                    break;
                case 10:
                    esito = Tool.use10(i1, this, p1, i2);
                    if (esito)
                        usedTcAndPlacedDiceA();
                    break;
                case 11:
                    esito = Tool.use11(i1, this, p1, i2, i3);
                    if (esito)
                        usedTcAndPlacedDiceA();
                    break;
                case 12:
                    esito = Tool.use12(i1, this, p1, p2, p3, p4, pr);
                    if (esito)
                        usedTcA();
                    break;
                default:
                    break;
            }
            if (esito) {
                setTokens(tokens - game.getTCtokens(i1));
                game.addTCtokens(i1);


                Logger.log(game + " player " + uUID + " effectively used " +
                        game.revealToolCard(nCard));
                return true;
            }
        }
        Logger.log(game + " player " + uUID + " attempt of unauthorized usage of ToolCard");
        return false;

    }

    /**
     * this is a updateview called from client. It comes in handy when the client
     * want to know immediately how things are.
     * each player can request this only in their turn.
     *
     * @param uUID is the code of the player
     */
    public synchronized void updateViewFromC(String uUID) {
        game.updateView(uUID);
    }

    /**
     * @param possibleWindows are the windows the server allows to each player
     *                        during initialization of game
     */
    public void setPossibleWindows(List<Integer> possibleWindows) {
        this.possibleWindows = new ArrayList<>(possibleWindows);
    }

    public String getNickName() {
        return nickName;
    }

    /**
     * @return the score of this player, computed considering private card, tokens, and public cards
     */
    public Integer getScore() {

        score = game.usePublicO(this.overlay);

        int i = 0;
        int j = 0;

        while (i < 4) {
            while (j < 5) {
                Dice dice = overlay.getDice(new Position(i, j));
                if (dice != null && dice.getColor().equals(privateO))
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

    /**
     * nice cheats..
     */
    private void hack() {
        if (nickName.equals("arna") || nickName.equals("fili") || nickName.equals("affo") || nickName.equals("cugola")) {
            this.window = MatchManager.getWindows().get(0);
            this.setTokens();
        }
    }

    public Integer getComputatedScore() {
        return this.score;
    }

    private boolean placedDiceQ() {
        return this.hasPlacedDice;
    }

    public boolean usedTcQ() {
        return this.hasUsedTc;
    }

    public boolean usedTcAndPlacedDiceQ() {
        return this.hasUsedTc || this.hasPlacedDice;

    }

    private void placedDiceA() {
        this.hasPlacedDice = true;
    }

    private void usedTcA() {
        this.hasUsedTc = true;
    }

    private void usedTcAndPlacedDiceA() {
        this.hasUsedTc = true;
        this.hasPlacedDice = true;
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

    /**
     * player chose window
     *
     * @param n in the arraylist of windows
     * @return true if it is allowed to do so, false otherwise
     */
    public synchronized boolean setWindowFromC(Integer n) {
        if (this.window != null) {
            Logger.log("Player: " + uUID + " Server already assigned Window for this player");
            return false;
        }
        if (n != null && this.possibleWindows.contains(n)) {
            this.window = MatchManager.getWindows().get(n);
            setTokens();
            hack();
            Logger.log("Player: " + uUID + " choose " + game.revealWindow(n) + ". It has: " + window.getTokens() + " tokens");
            return true;
        }
        Logger.log("Player: " + uUID + " Attempt to set improper Window");
        return false;
    }

    /**
     * set window
     *
     * @param n to this player
     */
    public synchronized void setWindow(Integer n) {
        this.window = MatchManager.getWindows().get(n);
        setTokens();
        hack();
        Logger.log(game + " player " + uUID + " server assigned Window n° " + n + ". It has " + window.getTokens() +
                " tokens. Will be forced start client-side");
    }

    private void setTokens() {
        this.tokens = this.window.getTokens();
    }

    public Pool getPool() {
        return pool;
    }

    /**
     * check if can be placed a dice from the pool in the position
     *
     * @param index    of the pool and in the position
     * @param position of the
     * @see Overlay
     * @return whether this can be done or not
     */
    public synchronized Boolean placeDice(Integer index, Position position) {

        if (!(placedDiceQ() || !pool.validateBusy(index) || !overlay.validateEmpty(position))) {
            Dice dice;
            dice = pool.getDice(index);
            if (window.placeDiceFromPool(this, index, position)) {
                placedDiceA();
                lastPlacedFromPool = position;
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