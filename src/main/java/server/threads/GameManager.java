package server.threads;

import server.*;
import server.connection.DummyMiddlewareServer;
import server.connection.MiddlewareServer;
import server.executable.PublicObject;
import server.executable.Tool;
import server.Window;
import shared.*;
import server.Player;
import shared.TransferObjects.*;
import shared.concurrency.GeneralTask;

import java.util.*;

public class GameManager extends GeneralTask {

    private Integer code;
    private final ArrayList<String> publicRef = new ArrayList<>();
    //private MiddlewareServer middlewareServer = MiddlewareServer.getInstance();
    private DummyMiddlewareServer middlewareServer = DummyMiddlewareServer.getInstance();
    private final ArrayList<String> players;
    private ArrayList<String> players2 = new ArrayList<>();
    private final Integer timeout1; //timer to play for each player config
    private final Integer timeout2; //connection issue config
    private final Integer timeout3; //pausetta config
    private final Integer timeout4; //for window back
    private final ArrayList<Player> vPlayersFixed = new ArrayList<>();
    private ArrayList<Player> vPlayers = new ArrayList<>();
    private MatchManager matchManager = MatchManager.getInstance();
    private boolean action = false;
    private ArrayList<Integer> publicOCs = new ArrayList<>();
    private ArrayList<Integer> toolCards = new ArrayList<>();
    private ArrayList<Integer> tCtokens = new ArrayList<>();
    private ArrayList<String> privateLeft = new ArrayList<>();
    private ArrayList<String> jump = new ArrayList<>();
    private String tavolo;
    private ArrayList<String> unresponsive = new ArrayList<>();
    private ArrayList<String> active = new ArrayList<>();
    private String expected = "none";
    private RoundTrack roundTrack = new RoundTrack();
    private boolean check1 = false;
    private ArrayList<Dice> dices = new ArrayList<>();
    private ArrayList<Dice> pool = new ArrayList<>();
    private final Object obj = new Object();
    private final Object obj2 = new Object(); //connection issues
    private final Object obj3 = new Object();

    public GameManager(ArrayList<String> players) {

        Random rand = new Random();
        this.code = MainServer.addGameManagers(this);
        this.players = players;
        this.publicRef.addAll(Collections.unmodifiableList(players));
        this.timeout1 = 10000;
        this.timeout2 = 12000;
        this.timeout3 = 5000;
        this.timeout4 = 3000;


        System.out.println("GameManager: " + this + ". Game started with " + players.size() +
                " players. They are: ");
        for (String player :
                players) {
            System.out.println(player + "; ");
        }
        System.out.println("\nServer wishes them good luck.\n");

        System.out.println("GameManager: "+this+", those are the configuration parameters: \n" +
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

        i = 1;

        while (i <= 90) {
            if (1 <= i && i <= 18)
                dices.add(new Dice('r', 1 + rand.nextInt(5)));
            else if (19 <= i && i <= 36)
                dices.add(new Dice('y', 1 + rand.nextInt(5)));
            else if (37 <= i && i <= 54)
                dices.add(new Dice('g', 1 + rand.nextInt(5)));
            else if (55 <= i && i <= 72)
                dices.add(new Dice('b', 1 + rand.nextInt(5)));
            else if (73 <= i && i <= 90)
                dices.add(new Dice('p', 1 + rand.nextInt(5)));
            i++;
        }

        i = 0;
        int j = 0;

        ArrayList<Character> ch = new ArrayList<>();
        ch.add('b');
        ch.add('g');
        ch.add('y');
        ch.add('v');
        ch.add('r');

        ArrayList<Integer> a = new ArrayList<>();

        j = rand.nextInt(4);
        while (i < players.size()) {
            while (a.contains(j)) {
                j = rand.nextInt(4);
            }
            a.add(j);
            i++;
        }
        i = 0;

        while (i < players.size()) {
            vPlayers.get(i).setPrivateOC(ch.get(a.get(i)));
            i++;
        }

        i = 0;
        a.clear();

        j = rand.nextInt(11);
        while (i < 3) {
            while (a.contains(j)) {
                j = rand.nextInt(11);
            }
            a.add(j);
            i++;
        }
        i = 0;

        toolCards.add(a.get(0));
        toolCards.add(a.get(1));
        toolCards.add(a.get(2));
        System.out.println("GameManager: " + this + " assigned " +
                "Tool cards n° " + revealToolCard(a.get(0)) + ", " + revealToolCard(a.get(1))
                + ", " + revealToolCard(a.get(2)));

        i = 0;
        a.clear();

        j = rand.nextInt(9);
        while (i < 3) {
            while (a.contains(j)) {
                j = rand.nextInt(9);
            }
            a.add(j);
            i++;
        }
        i = 0;

        publicOCs.add(a.get(0));
        publicOCs.add(a.get(1));
        publicOCs.add(a.get(2));
        System.out.println("GameManager: " + this + " assigned " +
                "Public Objective cards n° " + revealPublicOC(a.get(0)) + ", "
                + revealPublicOC(a.get(1)) + ", " + revealPublicOC(a.get(2)));

    }

    private void setAction(boolean action) {
        synchronized (obj3) {
            this.action = action;
            obj3.notifyAll();
        }
    }

    @Override
    public String toString() {
        return code.toString();
    }

    public ArrayList<String> getJump() {
        return jump;
    }

    public String getTavolo() {
        return tavolo;
    }

    public Integer getTimeout1() {
        return timeout1;
    }

    private synchronized void setExpected(String access) {
        System.out.println("\nGameManager: " + this + " Access granted to: " + access+"\n");
        this.expected = access;
    }

    private void pause(Integer millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Dice> getDices() {
        return dices;
    }

    public ArrayList<Dice> getPool() {
        return pool;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public String revealPublicOC(Integer i) {
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
        }
        return "card not found";
    }

    public String revealToolCard(Integer i) {
        i++;
        switch (i) {
            case 1:
                return "ToolC1";
            case 2:
                return "Tool2";
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
        }
        return "card not found";
    }

    public String revealWindow(Integer i) {
        return "Window?";
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

    public void updateView(String uuid) {
        ArrayList<PlayerT> vPlayersT = new ArrayList<>();
        System.out.println("GameManager: " + this + " updating view for client: " + uuid + ". \nThis is its state:");
        for (Player player :
                this.vPlayersFixed) {
            Window window = player.getWindow();
            WindowT windowT = new WindowT(window.getName(), window.getMatrices());
            PlayerT playerT = new PlayerT(player.getPrivateO(), windowT, player.getOverlay(),
                    player.getTokens(), player.getTurno(), player.getScore(), player.getPrivateTurn(),
                    player.getLastPlaced());
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

        Player player = SReferences.getPlayerRef(uuid);

        System.out.println("Player: " + uuid + " has " + player.getTokens() + " tokens, is at turn n° " + player.getTurno() +
                ", has " + player.getScore() + " points, is at private turn n° " + player.getPrivateTurn() +
                ", last dice placed was in position " + player.getLastPlaced().toString());

        middlewareServer.updateView(uuid, new GameManagerT(vPlayersT, publicOCsT,
                toolCsT, roundTrack, pool, tCtokens, publicRef.indexOf(uuid)));
    }

    public void updateView() {
        System.out.println("GameManager: " + this + " updating view: \nThis is the game's state:" +
                "\nIt has roundtrack with " + roundTrack.sumDices() + " dices on it, has " + pool.size() + " dices in the pool, " +
                "Tool cards have respectively " + tCtokens.get(0) + ", " + tCtokens.get(1) + ", " + tCtokens.get(2) + " tokens");
        for (String player :
                active) {
            updateView(player);
        }
    }

    public <T> Integer count(ArrayList<T> ts) {
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
            }
        }
        return score;
    }

    public Boolean useTool(String uUID, Integer i1, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {
        Boolean esito = false;

        if (!(i1 == null || i1 < 0 || i1 > 2)) {

            switch (toolCards.get(i1) + 1) {
                case 1:
                    esito = Tool.use1(this, i1, SReferences.getPlayerRef(uUID), p1, p2, p3, p4, pr, i2, i3);
                    break;
                case 2:
                    esito = Tool.use2(this, i1, SReferences.getPlayerRef(uUID), p1, p2, p3, p4, pr, i2, i3);
                    break;
                case 3:
                    esito = Tool.use3(this, i1, SReferences.getPlayerRef(uUID), p1, p2, p3, p4, pr, i2, i3);
                    break;
                case 4:
                    esito = Tool.use4(this, i1, SReferences.getPlayerRef(uUID), p1, p2, p3, p4, pr, i2, i3);
                    break;
                case 5:
                    esito = Tool.use5(this, i1, SReferences.getPlayerRef(uUID), p1, p2, p3, p4, pr, i2, i3);
                    break;
                case 6:
                    esito = Tool.use6(this, i1, SReferences.getPlayerRef(uUID), p1, p2, p3, p4, pr, i2, i3);
                    break;
                case 7:
                    esito = Tool.use7(this, i1, SReferences.getPlayerRef(uUID), p1, p2, p3, p4, pr, i2, i3);
                    break;
                case 8:
                    esito = Tool.use8(this, i1, SReferences.getPlayerRef(uUID), p1, p2, p3, p4, pr, i2, i3);
                    break;
                case 9:
                    esito = Tool.use9(this, i1, SReferences.getPlayerRef(uUID), p1, p2, p3, p4, pr, i2, i3);
                    break;
                case 10:
                    esito = Tool.use10(this, i1, SReferences.getPlayerRef(uUID), p1, p2, p3, p4, pr, i2, i3);
                    break;
                case 11:
                    esito = Tool.use11(this, i1, SReferences.getPlayerRef(uUID), p1, p2, p3, p4, pr, i2, i3);
                    break;
                case 12:
                    esito = Tool.use12(this, i1, SReferences.getPlayerRef(uUID), p1, p2, p3, p4, pr, i2, i3);
                    break;
            }
            if (esito) {
                System.out.println("GameManager: " + this + " player " + uUID + " effectively used Tool card" +
                        " n°" + i1);
                return true;
            }
        }
        System.out.println("GameManager: " + this + " player " + uUID + " attempt of unauthorized usage of Tool card");
        return false;

    }

    public void shiftPlayers() {
        String temp;
        temp = players.remove(0);
        players.add(temp);
    }

    public void throwDice() {
        pool.clear();
        Integer num = 2 * players2.size() + 1;
        int i = 0;
        Random rand = new Random();

        while (i < num) {
            pool.add(dices.remove(rand.nextInt(dices.size() - 1)));
            i++;
        }
    }

    public void settleRoundtrack(Integer col) {
        for (Dice dice :
                pool) {
            if (dice != null)
                roundTrack.setDice(dice, col);
        }
    }

    public void endTurn(String uUID) {
        setAction(true);
    }

    public void exitGame2(String loser) {
        synchronized (MatchManager.getObj()) {
            MatchManager.getLeft().add(loser);
        }
        synchronized (obj) {
            this.privateLeft.add(loser);
        }
    }

    public void checkActive() {
        for (String pla : players2
                ) {
            if (privateLeft.contains(pla)) {
                active.remove(pla);
                unresponsive.remove(pla);
            }

            if (middlewareServer.ping(pla)) {
                if (!active.contains(pla))
                    active.add(pla);
                unresponsive.remove(pla);
            } else {
                if (!unresponsive.contains(pla))
                    unresponsive.add(pla);
                active.remove(pla);
            }
        }
    }

    private void closeGame() {
        for (String player :
                players) {
            synchronized (MatchManager.getObj()){
                MatchManager.getLeft().remove(players);
            }
            synchronized (MatchManager.getObj2()) {
                SReferences.removeRef(player);
            }
        }
    }

    @Override
    public void run() {
        super.run();

        Random rand = new Random();
        Integer i = 0;
        Integer j = 0;
        ArrayList<Integer> a = new ArrayList<>();

        j = rand.nextInt(22);
        while (i < 2 * players.size()) {
            while (a.contains(j) || j % 2 == 1) {
                j = rand.nextInt(22);
            }
            a.add(j);
            a.add(j + 1);
            i++;
        }
        i = 0;

        while (i < players.size()) {
            Integer k;
            ArrayList<Integer> b = new ArrayList<>();
            ArrayList<Cell[][]> matrices = new ArrayList<>();
            b.addAll(a.subList(((i) * 4), ((i + 1) * 4)));
            SReferences.getPlayerRef(players.get(i)).setPossibleWindows(b);
            System.out.println("Player: " + players.get(i) + " can chose its Window among the following: " +
                    revealWindow(b.get(0)) + ", " + revealWindow(b.get(1)) +
                    ", " + revealWindow(b.get(2)) + ", " + revealWindow(b.get(3)));
            k = 0;
            for (Integer y :
                    b) {
                matrices.add(matchManager.getWindows().get(y).getMatrices());
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
        boolean t = true;

        for (String player :
                players) {
            vPlayer = SReferences.getPlayerRef(player);
            if (vPlayer.getWindow() == null) {
                vPlayer.setWindow(a.get(4 * i + rand.nextInt(3)));
                middlewareServer.startGameViewForced(vPlayer.getuUID());
            }
            i++;
        }

        pause(timeout3);

        System.out.println("\nGameManager: " + this + " Initialization sequence completed");

        pause(2000);

        j = 1;
        int k;
        while (j <= 10) {

            for (String p :
                    players) {
                if (middlewareServer.ping(p)) {
                    synchronized (MatchManager.getObj()) {
                        MatchManager.getLeft().remove(p);
                    }
                    synchronized (obj) {
                        privateLeft.remove(p);
                    }
                }
            }

            players2.clear();
            players2.addAll(players);
            players2.removeAll(privateLeft);
            active.clear();
            unresponsive.clear();
            active.addAll(players2);
            //fitness(players2);

            checkActive();
            throwDice();
            i = 1;
            k = 1;
            while (k < 3) {
                for (String remotePlayer :
                        players2) {
                    Player localPlayer = SReferences.getPlayerRef(remotePlayer);
                    System.out.println("\n\n\nGameManager: " + this + " begin round: " + j + ", turn: " + i + "\n");
                    checkActive();

                    System.out.println("GameManager: " + this + " players online are " + active.size() +
                            ". They are: ");
                    for (String player :
                            active) {
                        System.out.println(player + "; ");
                    }

                    System.out.println("GameManager: " + this + " players temporarily offline " +
                            "are " + unresponsive.size() + ". They are: ");
                    for (String player :
                            unresponsive) {
                        System.out.println(player + "; ");
                    }

                    System.out.println("GameManager: " + this + " players who quit " +
                            "are " + privateLeft.size() + ". They are: ");
                    for (String player :
                            privateLeft) {
                        System.out.println(player + "; ");
                    }

                    System.out.println("GameManager: " + this + " we play with " + count(pool) + " dices\n");

                    //check if all left game
                    if (active.size() + unresponsive.size() == 0) {
                        System.out.println("GameManager: " + this + "seems that all quit the game. Bye.");
                        closeGame();
                        return;
                    }

                    int p = 1;
                    int q = 1;
                    //check if global blackout
                    if (active.isEmpty()) {
                        System.out.println("GameManager: " + this + "seems all are having issues over the net");
                        while (active.isEmpty()) {
                            System.out.println("GameManager: " + this + " Attempt to reconnect n° " + (p + 1));
                            for (String pla : players2
                                    ) {
                                if (middlewareServer.ping(pla)) {
                                    if (!active.contains(pla))
                                        active.add(pla);
                                    unresponsive.remove(pla);
                                } else {
                                    if (!unresponsive.contains(pla))
                                        unresponsive.add(pla);
                                    active.remove(pla);
                                }
                            }
                            if (p == 3) {
                                System.out.println("GameManager: " + this + " After 3 attempts game closes. Bye");
                                closeGame();
                                return;
                            }
                            synchronized (obj2) {
                                try {
                                    obj2.wait(timeout2);
                                } catch (InterruptedException e) {
                                    Logger.log("Interrupted Exception");
                                }
                            }

                            p++;
                        }
                    }
                    //check if only one guest
                    p = 1;
                    if (active.size() + unresponsive.size() == 1) {
                        System.out.println("GameManager: " + this + " we're having a victory decided by arbitration");
                        while (active.size() + unresponsive.size() == 1) {
                            tavolo = active.get(0);
                            if (tavolo == null) {
                                System.out.println("GameManager: " + this + " the only player is offline!");
                                tavolo = unresponsive.get(0);
                            }
                            if (middlewareServer.ping(tavolo)) {
                                middlewareServer.tavoloWin(active.get(0));
                                closeGame();
                                System.out.println("GameManager: " + this + " the winner is " + tavolo + "! Bye");
                                pause(15000);
                                return;
                            }

                            synchronized (obj2) {
                                try {
                                    obj2.wait(timeout2);
                                } catch (InterruptedException e) {
                                    Logger.log("Interrupted Exception");
                                }
                            }
                            if (p == 5) {
                                System.out.println("GameManager: " + this + " after 5 attempts game closes");
                                closeGame();
                                return;
                            }
                            p++;
                        }
                    }

                    //check if active, doesn't have a turn to jump, then go ahead
                    if (!privateLeft.contains(remotePlayer)) {
                        if (jump.contains(remotePlayer)) {
                            jump.remove(remotePlayer);
                            System.out.println("GameManager: " + this + " player: " + remotePlayer +
                                    "jump this turn");
                        } else if (active.contains(remotePlayer)) {
                            this.updateView();
                            setExpected(remotePlayer);
                            middlewareServer.enable(remotePlayer);

                            localPlayer.incrementTurn();

                            synchronized (obj3) {
                                while (!this.action) {
                                    try {
                                        System.out.println("GameManager: " + this + " waiting player "
                                                + remotePlayer + "'s move");
                                        obj3.wait(timeout1);
                                        setAction(true);
                                    } catch (InterruptedException ie) {
                                        Logger.log("Thread sleep was interrupted!");
                                        Logger.strace(ie);
                                        Thread.currentThread().interrupt();
                                    }
                                }
                                setExpected("none");
                                setAction(false);
                                middlewareServer.shut(remotePlayer);
                                localPlayer.clearUsedTcAndPlacedDice();
                            }
                        }
                    }
                    i++;
                }
                Collections.reverse(players2);
                k++;
            }
            settleRoundtrack(j);
            shiftPlayers();
            j++;
        }
        i = 0;
        int points = 0;
        int temp;


        for (Player player : vPlayers
                ) {
            temp = vPlayers.get(i).setScore();
            if (temp > points)
                points = temp;
            i++;
        }
        for (Player play : vPlayers
                ) {
            middlewareServer.printScore(play.getuUID(), play.getScore());
            if (play.getScore() == points) {
                System.out.println("GameManager: " + this + " the winner is player: " + play.getuUID() + "." +
                        "Congratulations!.");
                middlewareServer.setWinner(play.getuUID());
            }
        }
        closeGame();
        Logger.log("GameManager: " + this + ". We are done here! Bye!");
        pause(10000);
    }
}