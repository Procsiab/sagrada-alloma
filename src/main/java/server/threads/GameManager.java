package server.threads;

import server.*;
import server.abstractsServer.Window;
import shared.*;
import server.Player;
import shared.TransferObjects.*;
import shared.abstractsShared.PrivateOC;
import server.abstractsServer.PublicOC;
import server.abstractsServer.ToolC;
import shared.concurrency.GeneralTask;

import java.util.ArrayList;
import java.util.Random;

public class GameManager extends GeneralTask {

    private ArrayList<String> publicRef = new ArrayList<>();
    //private MiddlewareServer middlewareServer = MiddlewareServer.getInstance();
    private DummyMiddlewareServer middlewareServer = DummyMiddlewareServer.getInstance();
    private ArrayList<String> players = new ArrayList<>();
    private ArrayList<String> players2 = new ArrayList<>();
    private ArrayList<String> playersFixed = new ArrayList<>();
    private final Integer timeout1; //timer to play for each player config
    private final Integer timeout2; //connection issue config
    private final Integer timeout3; //pausetta config
    private final Integer timeout4; //for window back
    private final Integer nMates;
    private ArrayList<Player> vPlayersFixed = new ArrayList<>();
    private ArrayList<Player> vPlayers = new ArrayList<>();
    private MatchManager matchManager = MatchManager.getInstance();
    private boolean action = false;
    private ArrayList<PrivateOC> privateOCs = new ArrayList<>();
    private ArrayList<PublicOC> publicOCs = new ArrayList<>();
    private ArrayList<ToolC> toolCards = new ArrayList<>();
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
    private final ArrayList<Object> obj4;
    private final Object obj5 = new Object();
    private final Object obj6 = new Object(); //timer for windows

    public GameManager(ArrayList<String> players) {

        System.out.println("Game started with " + players.size() + ". They are: ");
        for (String player :
                players) {
            System.out.println(player + "; ");
        }
        Logger.log("\nServer wishes you good luck.");

        Random rand = new Random();
        this.publicRef.addAll(players);
        this.players.addAll(players);
        this.timeout1 = 10000;
        this.timeout2 = 12000;
        this.timeout3 = 5000;
        this.timeout4 = 3000;
        this.nMates = players.size();
        this.obj4 = new ArrayList<>(players.size());

        System.out.println("Those are the configuration parameters: \n" +
                "time given to\n" +
                "each player to choose what to do: " + timeout1 + "s\n" +
                "solve connection issue: " + timeout2 + "s\n" +
                "allow initialization of GUI environment " + timeout3 + "s\n" +
                "each player to choose the appropriate window: " + timeout4 + "s\n");

        int i;
        for (i = 0; i < players.size(); i++) {
            this.obj4.add(new Object());
        }

        i = 0;

        for (String client : players) {
            Player player = new Player(i, this, publicRef.get(i));
            vPlayers.add(player);
            vPlayersFixed.add(player);
            i++;
        }


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
        MainServer.addGameManagers(this);
    }

    public void setAction(boolean action) {
        synchronized (obj3) {
            this.action = action;
            obj3.notifyAll();
        }
    }

    public ArrayList<PublicOC> getPublicOCs() {
        return publicOCs;
    }

    public ArrayList<ToolC> getToolCards() {
        return toolCards;
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

    private void setExpected(String access) {
        System.out.println("GameManger: " + this + ". Access granted to:" + access + "\n");
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

    public String getExpected() {
        return expected;
    }

    public void setPool(ArrayList<Dice> pool) {
        this.pool = pool;
    }

    public Integer getTCtokens(Integer pos) {
        return tCtokens.get(pos);
    }

    public void addTCtokens(Integer pos) {
        tCtokens.set(pos, 2);
    }

    public void updateView(String uuid) {
        ArrayList<PlayerT> vPlayersT = new ArrayList<>();
        System.out.println("GameManager: " + this + " updating view for client: " + uuid + ". \n This is its state:");
        for (Player player :
                this.vPlayersFixed) {
            Window window = player.getWindow();
            WindowT windowT = new WindowT(window.getName(), window.getMatrices());
            System.out.println("Player: " + uuid + " has Window n° "
                    + MatchManager.getInstance().getWindows().indexOf(window) + ", has" +
                    "Private Objective Card n° " +
                    MatchManager.getInstance().getPrivateOCs().indexOf(player.getPrivateOC()) +
                    ", has " + player.getTokens() + " tokens, is at turn n° " + player.getTurno() +
                    ", has " + player.getScore() + " points, is at private turn n° " + player.getPrivateTurn() +
                    ", last dice placed was in position " + player.getLastPlaced().toString() + ".\n");
            PlayerT playerT = new PlayerT(player.getPrivateOC(), windowT, player.getOverlay(),
                    player.getTokens(), player.getTurno(), player.getScore(), player.getPrivateTurn(),
                    player.getLastPlaced());
            vPlayersT.add(playerT);
        }
        vPlayersT.trimToSize();

        ArrayList<PublicOCT> publicOCsT = new ArrayList<>();
        for (PublicOC card :
                publicOCs) {
            publicOCsT.add(new PublicOCT(card.name));
        }

        int i = 0;
        ArrayList<ToolCT> toolCsT = new ArrayList<>();
        for (ToolC card :
                toolCards) {
            toolCsT.add(new ToolCT(card.getName(), tCtokens.get(i), card.getDescription()));
            i++;
        }
        middlewareServer.updateView(uuid, new GameManagerT(vPlayersT, privateOCs, publicOCsT,
                toolCsT, roundTrack, pool, tCtokens, publicRef.indexOf(uuid)));
    }

    public void updateView() {
        System.out.println("GameManager: " + this + " has Tool card n° "
                + MatchManager.getInstance().getToolCs().indexOf(toolCards.get(0)) + ", " +
                +MatchManager.getInstance().getToolCs().indexOf(toolCards.get(1)) + ", " +
                +MatchManager.getInstance().getToolCs().indexOf(toolCards.get(2)) + ", has Public " +
                "Objective card n° "
                + MatchManager.getInstance().getPublicOCs().indexOf(publicOCs.get(0)) + ", " +
                +MatchManager.getInstance().getPublicOCs().indexOf(publicOCs.get(1)) + ", " +
                +MatchManager.getInstance().getPublicOCs().indexOf(publicOCs.get(2)) + ", has roundtrack with " +
                roundTrack.sumDices() + " dices on it, has " + pool.size() + " dices in the pool, " +
                "toolCard has respectively " + tCtokens.get(0) + ", " + tCtokens.get(1) + ", " + tCtokens.get(2) + "\n");
        for (String player :
                active) {
            updateView(player);
        }
    }

    public void shiftPlayers() {
        String temp;
        temp = players.remove(0);
        players.add(temp);
    }

    public void throwDice() {
        pool.clear();
        pool.trimToSize();
        Integer num = 2 * players2.size() + 1;
        int i = 0;
        Random rand = new Random();

        while (i < num) {
            pool.add(dices.set(rand.nextInt(dices.size() - 1), null));
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
            SReferences.removeRef(player);
        }
    }

    @Override
    public void run() {
        super.run();

        int i = 0;
        int j = 0;
        Random rand = new Random();
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
            vPlayers.get(i).setPrivateOC(a.get(i));
            i++;
        }
        i = 0;

        a.clear();

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

        Logger.log(a.toString());
        while (i < players.size()) {
            Integer k;
            ArrayList<Integer> b = new ArrayList<>();
            ArrayList<Cell[][]> matrices = new ArrayList<>();
            b.addAll(a.subList(((i) * 4), ((i + 1) * 4)));
            SReferences.getPlayerRefEnhanced(players.get(i)).setPossibleWindows(b);
            System.out.println("Player: " + players.get(i) + "can chose its window among the following:" +
                    b.toString() + "\n");
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
        try {
            synchronized (this.obj6) {
                this.obj6.wait(timeout2);
            }
        } catch (InterruptedException e) {
            Logger.log("Interrupted Exception");
            e.printStackTrace();
        }
        setExpected("none");

        try {
            synchronized (this.obj6) {
                this.obj6.wait(timeout4);
            }
        } catch (InterruptedException e) {
            Logger.log("Interrupted Exception");
            e.printStackTrace();
        }

        i = 0;
        int s = 0;
        Player vPlayer;
        boolean t = true;

        for (String player :
                players) {
            vPlayer = SReferences.getPlayerRefEnhanced(player);
            Logger.log(vPlayer.toString());
            if (vPlayer.getWindow() == null) {
                vPlayer.setWindow(a.get(4 * i + rand.nextInt(3)));
                middlewareServer.startGameViewForced(vPlayer.getuUID());
            }
            i++;
        }

        for (Player player : vPlayers) {
            player.setTokens();
        }


        s = 0;
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

        toolCards.add(matchManager.getToolCs().get(a.get(0)));
        toolCards.add(matchManager.getToolCs().get(a.get(1)));
        toolCards.add(matchManager.getToolCs().get(a.get(2)));
        System.out.println("GameManager: " + this + " assigned " +
                "Tool cards n° " + a.get(0) + ", " + a.get(1) + ", " + a.get(2) + "\n");

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

        publicOCs.add(matchManager.getPublicOCs().get(a.get(0)));
        publicOCs.add(matchManager.getPublicOCs().get(a.get(1)));
        publicOCs.add(matchManager.getPublicOCs().get(a.get(2)));
        System.out.println("GameManager: " + this + " assigned " +
                "Public Objective cards n° " + a.get(0) + ", " + a.get(1) + ", " + a.get(2) + "\n");

        i = 0;
        a.clear();

        Logger.log("GameManager: " + this + " Initialization sequence completed");

        j = 1;
        i = 1;
        int k = 1;
        boolean upward = true;
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
            i = -1;
            for (String remotePlayer :
                    players2) {
                Player localPlayer = SReferences.getPlayerRefEnhanced(remotePlayer);
                i++;
                System.out.println("GameManager: " + this + " begin turn " + i + " of round: " + j + "\n");
                checkActive();

                System.out.println("GameManager: " + this + " players online are " + active.size() +
                        ". They are: ");
                for (String player :
                        active) {
                    System.out.println(player + "; ");
                }

                System.out.println("\nGameManager: " + this + " players temporarily offline " +
                        "are " + unresponsive.size() + ". They are: ");
                for (String player :
                        unresponsive) {
                    System.out.println(player + "; ");
                }

                System.out.println("\nGameManager: " + this + " players who quit " +
                        "are " + privateLeft.size() + ". They are: ");
                for (String player :
                        privateLeft) {
                    System.out.println(player + "; ");
                }

                System.out.println("\nGameManager: " + this + "we play with " + pool.size() + " dices\n");

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
                    System.out.println("GameManager: " + this + "seems all are having issues over the net.\n");
                    while (active.isEmpty()) {
                        System.out.println("GameManager: " + this + " Attempt to reconnect n° " + (p + 1) + "\n");
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
                            System.out.println("GameManager: " + this + " After 3 attempts game closes");
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
                    System.out.println("GameManager: " + this + " we're having a victory decided by arbitration\n");
                    while (active.size() + unresponsive.size() == 1) {
                        tavolo = active.get(0);
                        if (tavolo == null) {
                            System.out.println("GameManager: " + this + " the only player is offline!\n");
                            tavolo = unresponsive.get(0);
                        }
                        if (middlewareServer.ping(tavolo)) {
                            middlewareServer.tavoloWin(active.get(0));
                            closeGame();
                            System.out.println("GameManager: " + this + " the winner is " + tavolo + "\n");
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
                            System.out.println("GameManager: " + this + " after 5 attempts game closes\n");
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
                                "jump this turn\n");
                    } else if (active.contains(remotePlayer)) {
                        this.updateView();
                        setExpected(remotePlayer);
                        middlewareServer.enable(remotePlayer);

                        localPlayer.incrementTurn();

                        synchronized (obj3) {
                            while (!this.action) {
                                try {
                                    System.out.println("GameManager: " + this + " waiting player "
                                            + remotePlayer + "'s move\n");
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
                if (upward) {
                    if (i == players2.size())
                        upward = false;
                    else
                        i++;
                } else
                    i--;
            }
            settleRoundtrack(j);
            shiftPlayers();
            upward = true;
            i = 1;
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
                        "Congratulations!.\n");
                middlewareServer.setWinner(play.getuUID());
            }
        }
        closeGame();
        Logger.log("GameManager: " + this + ". We are done here! Bye!");
        pause(10000);
    }
}