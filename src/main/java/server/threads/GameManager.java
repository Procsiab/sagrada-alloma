package server.threads;

import server.*;
import shared.Dice;
import shared.Logger;
import server.Player;
import shared.RoundTrack;
import shared.abstracts.PrivateOC;
import shared.abstracts.PublicOC;
import shared.abstracts.ToolC;
import shared.abstracts.Window;
import shared.TransferObjects.PlayerT;
import shared.logic.GeneralTask;
import shared.TransferObjects.GameManagerT;

import java.util.ArrayList;
import java.util.Random;

public class GameManager extends GeneralTask {

    public ArrayList<String> publicRef = new ArrayList<>();
    public MiddlewareServer middlewareServer = MiddlewareServer.getInstance();
    public ArrayList<String> players = new ArrayList<>();
    public ArrayList<String> players2 = new ArrayList<>();
    private final Integer sleepTime;
    private final Integer timeout2;
    private final Integer nMates;
    private ArrayList<Player> vPlayers = new ArrayList<>();
    public MatchManager matchManager = MatchManager.getInstance();
    private boolean action = false;
    public boolean dicePlaced = false;
    public boolean cardEnabled = false;
    public ArrayList<PrivateOC> privateOCs = new ArrayList<>();
    public ArrayList<PublicOC> publicOCs = new ArrayList<>();
    public ArrayList<ToolC> toolCards = new ArrayList<>();
    public ArrayList<Integer> tCtokens = new ArrayList<>();
    public ArrayList<String> privateLeft = new ArrayList<>();
    public ArrayList<String> jump = new ArrayList<>();
    public ArrayList<Boolean> jumpB = new ArrayList<>();
    public ArrayList<String> unresponsive = new ArrayList<>();
    public ArrayList<String> active = new ArrayList<>();
    public String expected;
    public RoundTrack roundTrack = new RoundTrack();
    public boolean check1 = false;
    public ArrayList<Dice> dices = new ArrayList<>();
    public ArrayList<Dice> pool = new ArrayList<>();
    public final Object obj = new Object();
    public final Object obj2 = new Object();
    public final Object obj3 = new Object();
    public final ArrayList<Object> obj4;

    public GameManager(ArrayList<String> players) {

        Random rand = new Random();
        this.publicRef.addAll(players);
        this.players.addAll(players);
        this.sleepTime = 10000;
        this.timeout2 = 5000;
        this.nMates = players.size();
        this.obj4 = new ArrayList<>(players.size());
        int i;
        for (i = 0; i < players.size(); i++) {
            this.obj4.add(new Object());
        }

        i = 0;

        for (String client : players) {
            vPlayers.add(new Player(i, this, publicRef.get(i)));
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

        while (i <= 90) {
            if (1 <= i && i <= 18)
                dices.add(new Dice('r', rand.nextInt(6)));
            else if (19 <= i && i <= 36)
                dices.add(new Dice('y', rand.nextInt(6)));
            else if (37 <= i && i <= 54)
                dices.add(new Dice('g', rand.nextInt(6)));
            else if (55 <= i && i <= 72)
                dices.add(new Dice('b', rand.nextInt(6)));
            else if (73 <= i && i <= 90)
                dices.add(new Dice('p', rand.nextInt(6)));
            i++;
        }


    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public boolean getAction() {
        return action;
    }

    public void setLoser(String loser) {
        this.privateLeft.add(loser);
        MatchManager.left.add(loser);
    }

    public void updateView(String uuid) {
        ArrayList<PlayerT> vPlayersT = new ArrayList<>();
        for (Player player :
                this.vPlayers) {
            vPlayersT.add(new PlayerT(player.privateOC, player.window, player.overlay,
                    player.turno, player.tokens, player.score, player.privateTurn, player.lastPlaced));
        }
        vPlayersT.trimToSize();
        middlewareServer.updateView(uuid, new GameManagerT(vPlayersT, privateOCs, publicOCs,
                toolCards, roundTrack, pool, tCtokens, players.indexOf(uuid)));
    }

    public void shiftPlayers() {
        String temp;
        temp = players.remove(0);
        players.add(temp);
    }

    public void throwDice() {
        pool.clear();
        int num = 2 * active.size() + 1;
        int i = 0;
        Random rand = new Random();

        while (i < num) {
            pool.add(dices.remove(rand.nextInt(dices.size() - 1)));
            i++;
        }

    }

    public void fitness(ArrayList a) {
        int i = 1;
        a.trimToSize();
        while (i <= a.size()) {
            if (a.get(i - 1) == null)
                a.remove(i - 1);
            i++;
        }
    }


    public void setToolCards(ArrayList<ToolC> toolCards) {
        this.toolCards = toolCards;
    }

    public void exitGame2(String uuid) {
        //TODO Implement method
    }

    @Override
    public void run() {
        super.run();
        Logger.log("GameManager has started");

        int i = 0;
        int j = 0;


        i = 0;

        /*for (String client : players) {
            try {
                middlewareServer.setSGame(client, this);
            } catch (RemoteException re) {
                Logger.log("Unable to reach client");
                re.printStackTrace();
            }
        }*/

        Random rand = new Random();
        ArrayList<Integer> a = new ArrayList<>();

        j = rand.nextInt(5);
        while (i < players.size()) {
            while (a.contains(j)) {
                j = rand.nextInt(5);
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

        j = rand.nextInt(24);
        while (i < 4 * players.size()) {
            while (a.contains(j)) {
                j = rand.nextInt(24);
            }
            a.add(j);
            i++;
        }
        i = 0;

        Logger.log(a.toString());

        while (i < players.size()) {
            Integer k;
            Logger.log("Choose window for player " + players.get(i));
            ArrayList<Integer> b = new ArrayList<>();
            b.addAll(a.subList(((i) * 4), ((i + 1) * 4)));
            Logger.log(b.toString());
            k=0;
            for (Integer y :
                    b) {
                y++;
                b.set(k,y);
                k++;
            }
            Logger.log(b.toString());
            middlewareServer.chooseWindow(players.get(i), b);
            b.clear();

            //vPlayers.get(i).setWindow(k);

            i++;
        }

        i = 0;
        int s = 0;
        Window window;
        Player vPlayer;
        boolean t = true;

        for (String player :
                players) {
            Logger.log("aaaiisis");
            vPlayer = SReferences.getPlayerRefEnhanced(player);
            Logger.log(vPlayer.toString());
            synchronized (obj4.get(i)) {
                try {
                    this.expected = player;
                    obj4.get(i).wait(timeout2);
                    this.expected = null;
                    if (vPlayer.window == null) {
                        vPlayer.setWindow(a.get(4 * i + rand.nextInt(3)));

                        middlewareServer.startGameViewForced(vPlayer.uUID);
                        Logger.log("startgame forced");
                    }
                } catch (InterruptedException e) {
                    Logger.log("Interrupted Exception");
                    e.printStackTrace();
                }
            }
            i++;
        }
        s = 0;

        a.clear();
        i = 0;
        j = rand.nextInt(4);
        while (i < players.size()) {
            while (a.contains(j)) {
                j = rand.nextInt(4);
            }
            a.add(j);
            i++;
        }
        i = 0;

        for (Player player : vPlayers) {
            player.setTokens();
        }


        i = 0;
        a.clear();

        j = rand.nextInt(12);
        while (i < 3) {
            while (a.contains(j)) {
                j = rand.nextInt(12);
            }
            a.add(j);
            i++;
        }
        i = 0;

        toolCards.add(matchManager.toolCs.get(a.get(0)));
        toolCards.add(matchManager.toolCs.get(a.get(1)));
        toolCards.add(matchManager.toolCs.get(a.get(2)));

        i = 0;
        a.clear();

        j = rand.nextInt(10);
        while (i < 3) {
            while (a.contains(j)) {
                j = rand.nextInt(10);
            }
            a.add(j);
            i++;
        }
        i = 0;

        publicOCs.add(matchManager.publicOCs.get(a.get(0)));
        publicOCs.add(matchManager.publicOCs.get(a.get(1)));
        publicOCs.add(matchManager.publicOCs.get(a.get(2)));

        i = 0;
        a.clear();

        throwDice();

        /*
        for (String player : players) {
            middlewareServer.updateView(player, this);
        }
*/

        Logger.log("start turn manager");

        j = 1;
        i = 1;
        int k = 1;
        boolean upward = true;
        while (j <= 10) {
            //fitness(left);

            for (String p :
                    players) {
                if (middlewareServer.ping(p)) {
                    synchronized (MatchManager.obj) {
                        MatchManager.left.remove(p);
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
            //set nPlayers (which will not change over one turn) for nDices ecc..

            while (k <= 2 * players2.size()) {

                //fitness(active);
                //fitness(unresponsive);

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

                //check if all left game
                if (active.size() + unresponsive.size() == 0) {
                    //fuck all off
                    return;
                }

                int p = 1;
                int q = 1;
                //check if global blackout
                while (active.isEmpty()) {
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
                    if (p == 3)
                        //fuck all off
                        return;
                    synchronized (obj2) {
                        try {
                            obj2.wait(timeout2);
                        } catch (InterruptedException e) {
                            Logger.log("Interrupted Exception");
                        }
                    }
                    p++;
                }

                //check if only one guest
                p = 1;
                while (active.size() + unresponsive.size() == 1) {
                    while (active.isEmpty() && unresponsive.size() == 1) {

                        if (privateLeft.contains(unresponsive.get(0))) {
                            //fuck off
                            return;
                        }

                        if (middlewareServer.ping(unresponsive.get(0))) {
                            active.add(unresponsive.get(0));
                            unresponsive.clear();
                        }

                        if (p == 3)
                            //fuck off
                            return;
                        synchronized (obj2) {
                            try {
                                obj2.wait(timeout2);
                            } catch (InterruptedException e) {
                                Logger.log("Interrupted Exception");
                            }
                        }
                        p++;
                    }
                    if (active.size() == 1 && unresponsive.isEmpty()) {

                        if (middlewareServer.ping(active.get(0))) {
                            middlewareServer.aPrioriWin(active.get(0));
                            return;
                        }
                        //why don't change ISP?
                        if (q == 3)
                            //fuck off
                            return;
                        synchronized (obj2) {
                            try {
                                obj2.wait(timeout2);
                            } catch (InterruptedException e) {
                                Logger.log("Interrupted Exception");
                            }
                        }
                        q++;
                    }

                }

                //check if active, doesn't have a turn to jump, then go ahead

                if (jump.contains(players.get(i - 1))) {
                    if (!jumpB.get(jump.indexOf(players.get(i - 1)))) {
                        jumpB.remove(jump.indexOf(players.get(i - 1)));
                        jump.remove(players.get(i - 1));
                    }
                    if (jumpB.get(jump.indexOf(players.get(i - 1))))
                        jumpB.set(jump.indexOf(players.get(i - 1)), false);
                }
                if (active.contains(players.get(i - 1)) && !jump.contains(players.get(i - 1))) {
                    this.updateView(players.get(i - 1));
                    this.expected = players.get(i - 1);
                    middlewareServer.enable(players.get(i - 1));

                    vPlayers.get(i - 1).turno++;

                    synchronized (obj3) {
                        while (!this.action) {
                            try {
                                Logger.log("listening for dice thrown or toolcard");
                                obj3.wait(sleepTime);
                                this.action = true;
                            } catch (InterruptedException ie) {
                                Logger.log("Thread sleep was interrupted!");
                                Logger.strace(ie);
                                Thread.currentThread().interrupt();
                            }
                        }
                        this.action = false;
                        this.expected = null;
                        //middlewareServer.shut(players.get(i - 1));
                    }
                }
                if (upward) {
                    if (i == players2.size())
                        upward = false;
                    else
                        i++;
                } else
                    i--;
                k++;
            }
            shiftPlayers();
            //now k doesn't update so while run once
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
            middlewareServer.printScore(play.uUID, play.getScore());
            if (play.getScore() == points)
                middlewareServer.setWinner(play.uUID);
        }
        Logger.log("END OF GAME");
    }
}