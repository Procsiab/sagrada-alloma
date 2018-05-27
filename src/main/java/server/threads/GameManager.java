package server.threads;

import server.*;
import shared.Dice;
import shared.Logger;
import server.Player;
import shared.RoundTrack;
import shared.TransferObjects.*;
import shared.abstractsShared.PrivateOC;
import server.abstractsServer.PublicOC;
import server.abstractsServer.ToolC;
import server.abstractsServer.Window;
import shared.logic.GeneralTask;

import java.util.ArrayList;
import java.util.Random;

public class GameManager extends GeneralTask {

    public ArrayList<String> publicRef = new ArrayList<>();
    public MiddlewareServer middlewareServer = MiddlewareServer.getInstance();
    public ArrayList<String> players = new ArrayList<>();
    public ArrayList<String> players2 = new ArrayList<>();
    private final Integer sleepTime;
    private final Integer timeout2;
    public final Integer timeout3; //for windows
    public final Integer timeout4; //pausetta
    private final Integer nMates;
    private ArrayList<Player> vPlayersFixed = new ArrayList<>();
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
    public final Object obj5 = new Object();

    public GameManager(ArrayList<String> players) {

        Random rand = new Random();
        this.publicRef.addAll(players);
        this.players.addAll(players);
        this.sleepTime = 10000;
        this.timeout2 = 5000;
        this.timeout3 = 8000;
        this.timeout4 = 2000;
        this.nMates = players.size();
        this.obj4 = new ArrayList<>(players.size());
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
        synchronized (obj3) {
            this.action = action;
        }
    }

    public boolean getAction() {
        return action;
    }

    public void updateView(String uuid) {
        ArrayList<PlayerT> vPlayersT = new ArrayList<>();

        for (Player player :
                this.vPlayersFixed) {
            WindowT windowT = new WindowT(player.window.name, player.window.cells);
            PlayerT playerT = new PlayerT(player.privateOC, windowT, player.overlay,
                    player.turno, player.tokens, player.score, player.privateTurn,
                    player.lastPlaced);
            vPlayersT.add(playerT);
        }
        vPlayersT.trimToSize();

        ArrayList<PublicOCT> publicOCsT = new ArrayList<>();
        for (PublicOC card :
                publicOCs) {
            publicOCsT.add(new PublicOCT(card.name));
        }

        ArrayList<ToolCT> toolCsT = new ArrayList<>();
        for (ToolC card :
                toolCards) {
            toolCsT.add(new ToolCT(card.name, card.tokensRequired));
        }

        middlewareServer.updateView(uuid, new GameManagerT(vPlayersT, privateOCs, publicOCsT,
                toolCsT, roundTrack, pool, tCtokens, players.indexOf(uuid)));
    }

    public void shiftPlayers() {
        String temp;
        temp = players.remove(0);
        players.add(temp);
    }

    public void throwDice() {
        pool.clear();
        Integer num = 2 * active.size() + 1;
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

    public void endTurn(String uUID) {
        setAction(true);
    }

    public void exitGame2(String loser) {
        this.privateLeft.add(loser);
        MatchManager.left.add(loser);
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
//join the following two loops
        while (i < players.size()) {
            Integer k;
            Logger.log("Choose window for player " + players.get(i));
            ArrayList<Integer> b = new ArrayList<>();
            b.addAll(a.subList(((i) * 4), ((i + 1) * 4)));
            Logger.log(b.toString());
            k = 0;
            for (Integer y :
                    b) {
                y++;
                b.set(k, y);
                k++;
            }
            Logger.log(b.toString());
            middlewareServer.chooseWindow(players.get(i), b);
            b.clear();

            //vPlayers.get(i).setWindow(k);

            i++;
        }

        this.expected = "all";
        synchronized (obj5) {
            try {
                obj5.wait(timeout2);
            } catch (InterruptedException e) {
                Logger.log("Interrupted Exception");
                e.printStackTrace();
            }
        }
        this.expected = "none";

        i = 0;
        int s = 0;
        Player vPlayer;
        boolean t = true;

        for (String player :
                players) {
            vPlayer = SReferences.getPlayerRefEnhanced(player);
            Logger.log(vPlayer.toString());
            if (vPlayer.window == null) {
                vPlayer.setWindow(a.get(4 * i + rand.nextInt(3)));
                middlewareServer.startGameViewForced(vPlayer.uUID);
                Logger.log("startgame forced " + vPlayer.window.name);
            }
            i++;
        }


        synchronized (obj5) {
            try {
                obj5.wait(timeout4);
            } catch (InterruptedException e) {
                Logger.log("Interrupted Exception");
                e.printStackTrace();
            }
        }


        i = 0;

        for (Player player : vPlayers) {
            player.tokens = player.window.tokens;
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

        //this is necessary since the tokens required may be affected
        toolCards = (ArrayList<ToolC>) toolCards.clone();

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

            checkActive();
            throwDice();

            while (k <= 2 * players2.size()) {

                String remotePlayer = players.get(i - 1);
                Player localPlayer = SReferences.getPlayerRefEnhanced(remotePlayer);

                checkActive();

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

                if (jump.contains(remotePlayer)) {
                    if (!jumpB.get(jump.indexOf(remotePlayer))) {
                        jumpB.remove(jump.indexOf(remotePlayer));
                        jump.remove(remotePlayer);
                    }
                    if (jumpB.get(jump.indexOf(remotePlayer)))
                        jumpB.set(jump.indexOf(remotePlayer), false);
                }
                if (active.contains(remotePlayer) && !jump.contains(remotePlayer)) {
                    this.updateView(remotePlayer);
                    this.expected = remotePlayer;
                    middlewareServer.enable(remotePlayer);

                    localPlayer.turno++;

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
                        this.expected = "none";
                        middlewareServer.shut(remotePlayer);
                        localPlayer.hasUsedTc = false;
                        localPlayer.hasPlacedDice = false;
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
            k = 1;
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
            middlewareServer.printScore(play.uUID, play.getScore());
            if (play.getScore() == points)
                middlewareServer.setWinner(play.uUID);
        }
        Logger.log("END OF GAME");
    }
}