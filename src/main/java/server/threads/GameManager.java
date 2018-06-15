package server.threads;

import server.*;
import server.connection.ProxyServer;
import server.executables.PublicObject;
import server.Window;
import shared.*;
import server.Player;
import shared.TransferObjects.*;
import server.concurrency.GeneralTask;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GameManager extends GeneralTask {

    private Integer code;
    private final ArrayList<String> publicRef = new ArrayList<>();
    private ProxyServer middlewareServer = ProxyServer.getInstance();
    private final ArrayList<String> players = new ArrayList<>();
    private ArrayList<String> players2 = new ArrayList<>();
    private final Integer timeout1; //timer to play for each player config
    private final Integer timeout2; //connection issue config
    private final Integer timeout3; //pausetta config
    private final Integer timeout4; //for window back
    private final ArrayList<Player> vPlayersFixed = new ArrayList<>();
    private ArrayList<Player> vPlayers = new ArrayList<>();
    private boolean action = false;
    private ArrayList<Integer> publicOCs = new ArrayList<>();
    private ArrayList<Integer> toolCards = new ArrayList<>();
    private ArrayList<Integer> tCtokens = new ArrayList<>();
    private Set<String> left = Collections.synchronizedSet(new HashSet<>());
    private Vector<String> jump = new Vector<>();
    private AtomicInteger threads = new AtomicInteger(0);
    private String tavolo;
    private Set<String> unrespAltoughP = new HashSet<>();
    private Set<String> active = new HashSet<>();
    private String expected = "none";
    private RoundTrack roundTrack = new RoundTrack();
    private ArrayList<Dice> dices = new ArrayList<>();
    private Pool pool = new Pool();
    private final Object obj = new Object();

    public GameManager(List<String> players) {

        this.code = MainServer.addGameManagers(this);
        this.players.addAll(players);
        this.publicRef.addAll(Collections.unmodifiableList(players));
        this.timeout1 = Config.timeout1;
        this.timeout2 = Config.timeout2;
        this.timeout3 = Config.timeout3;
        this.timeout4 = Config.timeout4;

        System.out.println("\n");

        Logger.log(this + ". Game started with " + players.size() +
                " players. They are: ");
        for (String player :
                players) {
            Logger.log(player + "; ");
        }
        System.out.println("\nServer wishes them good luck.\n");

        System.out.println(this + ", those are the configuration parameters: \n" +
                "time given to\n" +
                "\teach player to choose what to do: " + timeout1 / 1000 + "s\n" +
                "\tsolve connection issue: " + timeout2 / 1000 + "s\n" +
                "\tallow initialization of GUI environment " + timeout3 / 1000 + "s\n" +
                "\teach player to choose the appropriate window: " + timeout4 / 1000 + "s\n");

        int i = 0;

        for (String client : players) {
            Player player = new Player(this, client);
            vPlayers.add(player);
            i++;
        }
        vPlayersFixed.addAll(Collections.unmodifiableList(vPlayers));

        i = 1;

        for (String p :
                publicRef) {
            SReferences.addGameRef(p, this);
            SReferences.addPlayerRef(p, vPlayers.get(i - 1));
            i++;
        }

        i = 1;
        while (i <= 3) {
            tCtokens.add(1);
            i++;
        }

        setDicesBag();

        i = 0;

        ArrayList<Character> ch = new ArrayList<>();
        ch.add('b');
        ch.add('g');
        ch.add('y');
        ch.add('v');
        ch.add('r');

        ArrayList<Integer> a = set(0, 4, players.size());

        while (i < players.size()) {
            vPlayers.get(i).setPrivateOC(ch.get(a.get(i)));
            i++;
        }


        a = set(0, 11, 3);

        toolCards.add(a.get(0));
        toolCards.add(a.get(1));
        toolCards.add(a.get(2));
        Logger.log(this + " assigned " +
                "Tool cards n° " + revealToolCard(a.get(0)) + ", " + revealToolCard(a.get(1))
                + ", " + revealToolCard(a.get(2)));


        a = set(0, 9, 3);

        publicOCs.add(a.get(0));
        publicOCs.add(a.get(1));
        publicOCs.add(a.get(2));
        Logger.log(this + " assigned " +
                "Public Objective cards n° " + revealPublicOC(a.get(0)) + ", "
                + revealPublicOC(a.get(1)) + ", " + revealPublicOC(a.get(2)));

        //initialize some dices
        pool.addDice(new Dice('y', 2));
        pool.addDice(new Dice('b', 3));
        pool.addDice(new Dice('r', 6));
        pool.addDice(new Dice('b', 5));
        pool.addDice(new Dice('v', 1));
        pool.addDice(new Dice('g', 4));
        pool.addDice(new Dice('y', 6));
        pool.addDice(new Dice('g', 5));
        pool.addDice(new Dice('v', 2));
        pool.addDice(new Dice('b', 4));
        pool.addDice(new Dice('v', 2));
        pool.addDice(new Dice('r', 6));
        pool.addDice(new Dice('g', 4));
        pool.addDice(new Dice('g', 2));
        pool.addDice(new Dice('y', 3));
        pool.addDice(new Dice('v', 2));
        pool.addDice(new Dice('b', 5));
        pool.addDice(new Dice('y', 3));
        pool.addDice(new Dice('b', 2));
        pool.addDice(new Dice('g', 3));
        pool.addDice(new Dice('y', 5));
        pool.addDice(new Dice('r', 2));
        pool.addDice(new Dice('b', 5));

        Logger.log(this + " Initialization sequence completed\n");
        pause(5000);

    }

    private ArrayList<Integer> set(Integer lowerBound, Integer upperBound, Integer size) {
        ArrayList<Integer> a = new ArrayList<>();
        Random rand = new Random();
        int j = lowerBound + rand.nextInt(upperBound++);
        int i = 1;
        size++;

        while (i < size) {
            while (a.contains(j)) {
                j = lowerBound + rand.nextInt(upperBound);
            }
            a.add(j);
            i++;
        }
        return a;
    }

    private void setDicesBag() {
        int i = 1;
        Random rand = new Random();

        while (i <= 90) {
            if (1 <= i && i <= 18)
                dices.add(new Dice('r', 1 + rand.nextInt(6)));
            else if (19 <= i && i <= 36)
                dices.add(new Dice('y', 1 + rand.nextInt(6)));
            else if (37 <= i && i <= 54)
                dices.add(new Dice('g', 1 + rand.nextInt(6)));
            else if (55 <= i && i <= 72)
                dices.add(new Dice('b', 1 + rand.nextInt(6)));
            else if (73 <= i)
                dices.add(new Dice('v', 1 + rand.nextInt(6)));
            i++;
        }
    }

    private void setAction(boolean action) {
        synchronized (obj) {
            this.action = action;
            obj.notifyAll();
        }
    }

    private Boolean getAction() {
        return action;
    }

    public AtomicInteger getThreads() {
        return threads;
    }

    @Override
    public String toString() {
        return "GameManager: " + code.toString();
    }

    public Vector<String> getJump() {
        return jump;
    }

    public String getTavolo() {
        return tavolo;
    }

    public Integer getTimeout1() {
        return timeout1;
    }

    private synchronized void setExpected(String access) {
        Logger.log(this + " Access granted to: " + access);
        this.expected = access;
    }

    private void pause(Integer millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Logger.log(e);
            Thread.currentThread().interrupt();
        }
    }

    public ArrayList<Dice> getDices() {
        return dices;
    }

    public Pool getPool() {
        return pool;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public void setPublicOCs(List<Integer> publicOCs) {
        this.publicOCs.addAll(publicOCs);
    }

    public void setToolCards(List<Integer> toolCards) {
        //only for testing
        this.toolCards.clear();
        this.toolCards.addAll(toolCards);
    }

    public String revealPublicOC(Integer i) {
        if (i == null)
            return "null";

        i++;
        switch (i) {
            case 1:
                return "PublicOC1";
            case 2:
                return "PublicOC2";
            case 3:
                return "PublicOC3";
            case 4:
                return "PublicOC4";
            case 5:
                return "PublicOC5";
            case 6:
                return "PublicOC6";
            case 7:
                return "PublicOC7";
            case 8:
                return "PublicOC8";
            case 9:
                return "PublicOC9";
            case 10:
                return "PublicOC10";
            default:
                return "card not found";
        }
    }

    public String revealToolCard(Integer i) {
        if (i == null)
            return "null";

        i++;
        switch (i) {
            case 1:
                return "ToolC1";
            case 2:
                return "ToolC2";
            case 3:
                return "ToolC3";
            case 4:
                return "ToolC4";
            case 5:
                return "ToolC5";
            case 6:
                return "ToolC6";
            case 7:
                return "ToolC7";
            case 8:
                return "ToolC8";
            case 9:
                return "ToolC9";
            case 10:
                return "ToolC10";
            case 11:
                return "ToolC11";
            case 12:
                return "ToolC12";
            default:
                return "card not found";
        }
    }

    public String revealWindow(Integer i) {
        if (i == null)
            return "null";

        i++;
        switch (i) {
            case 1:
                return "Window1";
            case 2:
                return "Window2";
            case 3:
                return "Window3";
            case 4:
                return "Window4";
            case 5:
                return "Window5";
            case 6:
                return "Window6";
            case 7:
                return "Window7";
            case 8:
                return "Window8";
            case 9:
                return "Window9";
            case 10:
                return "Window10";
            case 11:
                return "Window11";
            case 12:
                return "Window12";
            case 13:
                return "Window13";
            case 14:
                return "Window14";
            case 15:
                return "Window15";
            case 16:
                return "Window16";
            case 17:
                return "Window17";
            case 18:
                return "Window18";
            case 19:
                return "Window19";
            case 20:
                return "Window20";
            case 21:
                return "Window21";
            case 22:
                return "Window22";
            case 23:
                return "Window23";
            case 24:
                return "Window24";
            default:
                return "card not found";
        }
    }

    public synchronized String getExpected() {
        return expected;
    }

    public Integer getTCtokens(Integer pos) {
        return tCtokens.get(pos);
    }

    public void addTCtokens(Integer pos) {
        tCtokens.set(pos, 2);
    }

    public void updateView(String uUID) {
        ArrayList<PlayerT> vPlayersT = new ArrayList<>();
        for (Player player :
                this.vPlayersFixed) {
            Window window = player.getWindow();
            WindowT windowT = new WindowT(window.getName(), window.getMatrices());
            PlayerT playerT = new PlayerT(player.getNickName(), player.getPrivateO(), windowT, player.getOverlay(),
                    player.getTokens(), player.getTurno(), player.getComputatedScore(), player.getPrivateTurn(),
                    player.getLastPlacedFromPool());
            vPlayersT.add(playerT);
        }
        vPlayersT.trimToSize();

        ArrayList<String> publicOCsT = new ArrayList<>();
        for (Integer card :
                publicOCs) {
            publicOCsT.add(revealPublicOC(card));
        }

        int i = 0;
        ArrayList<ToolCT> toolCsT = new ArrayList<>();
        for (Integer card :
                toolCards) {
            toolCsT.add(new ToolCT(revealToolCard(card), tCtokens.get(i)));
            i++;
        }

        try {
            middlewareServer.updateView(uUID, new GameManagerT(vPlayersT, publicOCsT,
                    toolCsT, roundTrack, pool.getDices(), tCtokens, active, players, publicRef.indexOf(uUID)));
        } catch (NullPointerException npe) {
            //Logger.log(this + " player " + uUID + ", remote application error");
        }
    }

    private void updateView() {

        for (String player :
                active) {
            updateView(player);
        }
    }

    private <T> Integer count(List<T> ts) {
        int i = 0;
        for (T t :
                ts) {
            if (t != null)
                i++;
        }
        return i;
    }

    public Integer usePublicO(Overlay overlay) {
        Integer score = 0;
        for (Integer card :
                publicOCs) {
            card++;
            switch (card) {
                case 1:
                    score = score + PublicObject.use1(overlay);
                    break;
                case 2:
                    score = score + PublicObject.use2(overlay);
                    break;
                case 3:
                    score = score + PublicObject.use3(overlay);
                    break;
                case 4:
                    score = score + PublicObject.use4(overlay);
                    break;
                case 5:
                    score = score + PublicObject.use5(overlay);
                    break;
                case 6:
                    score = score + PublicObject.use6(overlay);
                    break;
                case 7:
                    score = score + PublicObject.use7(overlay);
                    break;
                case 8:
                    score = score + PublicObject.use8(overlay);
                    break;
                case 9:
                    score = score + PublicObject.use9(overlay);
                    break;
                case 10:
                    score = score + PublicObject.use10(overlay);
                    break;
                default:
                    score = 0;
            }
        }
        return score;
    }

    private void shiftPlayers() {
        String temp;
        temp = players.remove(0);
        players.add(temp);
    }

    public List<Integer> getToolCards() {
        return toolCards;
    }

    private void throwDice() {
        pool.clear();
        Integer num = 2 * players2.size() + 1;
        int i = 0;
        Random rand = new Random();

        while (i < num) {
            pool.addDice(dices.remove(rand.nextInt(dices.size())));
            i++;
        }
    }

    private void settleRoundtrack(Integer col) {
        for (Dice dice :
                pool.getDices()) {
            if (dice != null)
                roundTrack.addDice(dice, col - 1);
        }
    }

    public void endTurn() {
        setAction(true);
    }

    public synchronized void exitGame2(String loser) {
        this.left.add(loser);
    }

    private void checkActive() {
        for (String pla : players2
                ) {
            if (middlewareServer.ping(pla)) {
                active.add(pla);
                unrespAltoughP.remove(pla);
            } else {
                unrespAltoughP.add(pla);
                active.remove(pla);
            }
            if (left.contains(pla)) {
                active.remove(pla);
                unrespAltoughP.remove(pla);
            }
        }
    }

    private void printStatusOfClients() {

        Logger.log(this + " players online are " + active.size() +
                ". They are: ");
        for (String player :
                active) {
            Logger.log(player + "; ");
        }

        Logger.log(this + " players temporarily offline " +
                "are " + unrespAltoughP.size() + ". They are: ");
        for (String player :
                unrespAltoughP) {
            Logger.log(player + "; ");
        }
        Logger.log(this + " players who quit " +
                "are " + left.size() + ". They are: ");
        for (String player :
                left) {
            Logger.log(player + "; ");
        }
        Logger.log(this + " we play with " +

                count(pool.getDices()) + " dices");

    }

    private void handleWindows() {
        Random rand = new Random();
        Integer i = 0;
        Integer j;
        ArrayList<Integer> a = new ArrayList<>();

        j = rand.nextInt(23);
        while (i < 2 * players.size()) {
            while (a.contains(j) || j % 2 == 1) {
                j = rand.nextInt(23);
            }
            a.add(j);
            a.add(j + 1);
            i++;
        }
        i = 0;

        while (i < players.size()) {
            Integer k;
            ArrayList<Integer> b = new ArrayList<>(a.subList(((i) * 4), ((i + 1) * 4)));
            ArrayList<Cell[][]> matrices = new ArrayList<>();
            SReferences.getPlayerRef(players.get(i)).setPossibleWindows(b);
            Logger.log("Player: " + players.get(i) + " can chose its Window among the following: " +
                    revealWindow(b.get(0)) + ", " + revealWindow(b.get(1)) +
                    ", " + revealWindow(b.get(2)) + ", " + revealWindow(b.get(3)));
            k = 0;
            for (Integer y :
                    b) {
                matrices.add(MatchManager.getWindows().get(y).getMatrices());
                y++;
                b.set(k, y);
                k++;
            }

            middlewareServer.chooseWindow(players.get(i), b, matrices);
            b.clear();
            matrices.clear();
            i++;
        }

        setExpected("all");
        pause(timeout4);
        setExpected("none");

        i = 0;
        Player vPlayer;

        for (String player :
                players) {
            vPlayer = SReferences.getPlayerRef(player);
            if (vPlayer.getWindow() == null) {
                vPlayer.setWindow(a.get(4 * i + rand.nextInt(4)));
                middlewareServer.startGameViewForced(vPlayer.getuUID());
            }
            i++;
        }
        pause(timeout3);
    }

    private synchronized void resetPlayers() {
        players2.clear();
        unrespAltoughP.clear();
        active.clear();
        players2.addAll(players);
        for (String p :
                players) {
            if (middlewareServer.ping(p)) {
                left.remove(p);
            }
        }
        players2.removeAll(left);
    }

    private Boolean globalBlackOut() {
        int p = 1;
        //check if global blackout
        while (active.isEmpty()) {
            if (p == 1)
                Logger.log(this + " seems all are having issues over the net");
            Logger.log(this + " attempt to reconnect n° " + p);

            checkActive();
            if (p == 3) {
                Logger.log(this + " After 3 attempts game closes. Bye");
                closeGame();
                return true;
            }
            pause(timeout2);
            p++;
        }
        return false;
    }

    private Boolean allQuit() {
        if (players2.isEmpty()) {
            Logger.log(this + " seems that all quit the game. Bye.");
            closeGame();
            return true;
        }
        return false;
    }

    private Boolean onlyOne() {
        int p = 1;
        while (players2.size() == 1) {
            if (p == 1)
                Logger.log(this + " we're having a victory decided by arbitration");

            tavolo = players2.get(0);
            if (middlewareServer.ping(tavolo)) {
                middlewareServer.tavoloWin(tavolo);
                closeGame();
                Logger.log(this + " the winner is " + tavolo + "! Bye");
                pause(15000);
                return true;
            } else {
                Logger.log(this + " the only player is offline!");
            }

            pause(timeout2);

            if (p == 5) {
                Logger.log(this + " after 5 attempts game closes");
                closeGame();
                return true;
            }
            p++;
        }
        return false;
    }

    private void handleEffectiveTurn(String remotePlayer, Player localPlayer) {
        if (jump.remove(remotePlayer)) {
            Logger.log(this + " player: " + remotePlayer +
                    "jump this turn");
        } else if (active.contains(remotePlayer)) {
            this.updateView();
            setExpected(remotePlayer);
            localPlayer.clearUsedTcAndPlacedDice();
            localPlayer.incrementTurn();
            middlewareServer.enable(remotePlayer);

            synchronized (obj) {
                while (!getAction()) {
                    try {
                        Logger.log(this + " waiting player "
                                + remotePlayer + "'s move");
                        obj.wait(timeout1);
                        setAction(true);
                    } catch (InterruptedException ie) {
                        Logger.log("Thread sleep was interrupted!");
                        Logger.strace(ie);
                        Thread.currentThread().interrupt();
                    }
                }
                setExpected("none");
                middlewareServer.shut(remotePlayer);
                while (threads.get() != 0) {
                }
                setAction(false);
            }
        }
    }

    private void scoringPhase() {
        int points = 0;
        int temp;


        for (Player player : vPlayers
                ) {
            temp = player.getScore();
            if (temp > points)
                points = temp;
        }
        for (Player play : vPlayers
                ) {
            middlewareServer.printScore(play.getuUID(), play.getComputatedScore());
            if (play.getScore().equals(points)) {
                Logger.log(this + " the winner is player: " + play.getuUID() + "." +
                        "Congratulations!.");
                middlewareServer.setWinner(play.getuUID());
            }
        }
    }

    private void closeGame() {
        for (String player :
                players) {
            SReferences.removeRef(player);
        }
    }

    @Override
    public void run() {
        super.run();

        handleWindows();

        int i;
        int j = 1;
        int k;
        while (j <= 10) {
            resetPlayers();
            throwDice();
            i = 1;
            k = 1;
            while (k < 3) {
                for (String remotePlayer :
                        players2) {
                    System.out.println("\n\n\n");
                    Logger.log(this +
                            " begin round: " + j + ", turn: " + i + "\n");
                    Player localPlayer = SReferences.getPlayerRef(remotePlayer);

                    checkActive();
                    printStatusOfClients();
                    if (globalBlackOut())
                        return;
                    if (onlyOne())
                        return;
                    handleEffectiveTurn(remotePlayer, localPlayer);
                    i++;
                }
                Collections.reverse(players2);
                k++;
            }
            settleRoundtrack(j);
            shiftPlayers();
            j++;
        }

        if (allQuit())
            return;

        scoringPhase();
        closeGame();
        Logger.log(this + ". We are done here! Bye!");
        pause(10000);
    }

}