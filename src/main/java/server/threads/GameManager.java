package server.threads;

import server.*;
import server.abstractsServer.Window;
import shared.Dice;
import shared.Logger;
import server.Player;
import shared.RoundTrack;
import shared.TransferObjects.*;
import shared.abstractsShared.PrivateOC;
import server.abstractsServer.PublicOC;
import server.abstractsServer.ToolC;
import shared.concurrency.GeneralTask;

import java.util.ArrayList;
import java.util.Random;

public class GameManager extends GeneralTask {

    private ArrayList<String> publicRef = new ArrayList<>();
    private MiddlewareServer middlewareServer = MiddlewareServer.getInstance();
    private ArrayList<String> players = new ArrayList<>();
    private ArrayList<String> players2 = new ArrayList<>();
    private ArrayList<String> playersFixed = new ArrayList<>();
    private final Integer sleepTime; //config
    private final Integer timeout2; //config
    private final Integer timeout3; //for windows config
    private final Integer timeout4; //pausetta config
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
    private ArrayList<String> unresponsive = new ArrayList<>();
    private ArrayList<String> active = new ArrayList<>();
    private String expected;
    private RoundTrack roundTrack = new RoundTrack();
    private boolean check1 = false;
    private ArrayList<Dice> dices = new ArrayList<>();
    private ArrayList<Dice> pool = new ArrayList<>();
    private final Object obj = new Object();
    private final Object obj2 = new Object();
    private final Object obj3 = new Object();
    private final ArrayList<Object> obj4;
    private final Object obj5 = new Object();

    public GameManager(ArrayList<String> players) {

        Random rand = new Random();
        this.expected = "none";
        this.publicRef.addAll(players);
        this.players.addAll(players);
        this.sleepTime = 10000;
        this.timeout2 = 8000;
        this.timeout3 = 5000;
        this.timeout4 = 8000;
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

        i =1;
        while (i<=3) {
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


    }

    public void setAction(boolean action) {
        synchronized (obj3) {
            this.action = action;
        }
    }

    public boolean getAction() {
        return action;
    }

    public void settCtokens(ArrayList<Integer> tCtokens) {
        this.tCtokens = tCtokens;
    }

    public void setPublicOCs(ArrayList<PublicOC> publicOCs) {
        this.publicOCs = publicOCs;
    }

    public void setPrivateOCs(ArrayList<PrivateOC> privateOCs) {
        this.privateOCs = privateOCs;
    }

    public void setMatchManager(MatchManager matchManager) {
        this.matchManager = matchManager;
    }

    public MatchManager getMatchManager() {
        return matchManager;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public ArrayList<Player> getvPlayers() {
        return vPlayers;
    }

    public ArrayList<Player> getvPlayersFixed() {
        return vPlayersFixed;
    }

    public ArrayList<PrivateOC> getPrivateOCs() {
        return privateOCs;
    }

    public ArrayList<PublicOC> getPublicOCs() {
        return publicOCs;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public ArrayList<String> getPlayers2() {
        return players2;
    }

    public ArrayList<String> getPlayersFixed() {
        return playersFixed;
    }

    public ArrayList<String> getPrivateLeft() {
        return privateLeft;
    }

    public ArrayList<String> getPublicRef() {
        return publicRef;
    }

    public ArrayList<ToolC> getToolCards() {
        return toolCards;
    }

    public boolean isAction() {
        return action;
    }

    public Integer getnMates() {
        return nMates;
    }

    public Integer getSleepTime() {
        return sleepTime;
    }

    public Integer getTimeout2() {
        return timeout2;
    }

    public Integer getTimeout3() {
        return timeout3;
    }

    public Integer getTimeout4() {
        return timeout4;
    }

    public MiddlewareServer getMiddlewareServer() {
        return middlewareServer;
    }

    public void setMiddlewareServer(MiddlewareServer middlewareServer) {
        this.middlewareServer = middlewareServer;
    }

    public void setPlayers2(ArrayList<String> players2) {
        this.players2 = players2;
    }

    public void setPlayersFixed(ArrayList<String> playersFixed) {
        this.playersFixed = playersFixed;
    }

    public void setPublicRef(ArrayList<String> publicRef) {
        this.publicRef = publicRef;
    }

    public void setvPlayers(ArrayList<Player> vPlayers) {
        this.vPlayers = vPlayers;
    }

    public ArrayList<String> getJump() {
        return jump;
    }

    public void setvPlayersFixed(ArrayList<Player> vPlayersFixed) {
        this.vPlayersFixed = vPlayersFixed;
    }

    public void setJump(ArrayList<String> jump) {
        this.jump = jump;
    }

    public Object getObj2() {
        return obj2;
    }

    public Object getObj() {
        return obj;
    }

    public void setPrivateLeft(ArrayList<String> privateLeft) {
        this.privateLeft = privateLeft;
    }

    public ArrayList<Dice> getDices() {
        return dices;
    }

    public ArrayList<Dice> getPool() {
        return pool;
    }

    public ArrayList<Object> getObj4() {
        return obj4;
    }

    public ArrayList<String> getActive() {
        return active;
    }

    public ArrayList<String> getUnresponsive() {
        return unresponsive;
    }

    public boolean isCheck1() {
        return check1;
    }

    public Object getObj3() {
        return obj3;
    }

    public Object getObj5() {
        return obj5;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public String getExpected() {
        return expected;
    }

    public void setActive(ArrayList<String> active) {
        this.active = active;
    }

    public void setCheck1(boolean check1) {
        this.check1 = check1;
    }

    public void setDices(ArrayList<Dice> dices) {
        this.dices = dices;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public void setPool(ArrayList<Dice> pool) {
        this.pool = pool;
    }

    public void setRoundTrack(RoundTrack roundTrack) {
        this.roundTrack = roundTrack;
    }

    public void setUnresponsive(ArrayList<String> unresponsive) {
        this.unresponsive = unresponsive;
    }

    public Integer getTCtokens(Integer pos) {
        return tCtokens.get(pos);
    }

    public void addTCtokens(Integer pos){
        tCtokens.set(pos,2);
    }

    public void updateView(String uuid) {
        ArrayList<PlayerT> vPlayersT = new ArrayList<>();

        for (Player player :
                this.vPlayersFixed) {
            Window window = player.getWindow();
            WindowT windowT = new WindowT(window.getName(), window.getCells());
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

        int i =0;
        ArrayList<ToolCT> toolCsT = new ArrayList<>();
        for (ToolC card :
                toolCards) {
            toolCsT.add(new ToolCT(card.getName(), tCtokens.get(i), card.getDescription()));
            i++;
        }

        System.out.println(publicRef.indexOf(uuid));
        middlewareServer.updateView(uuid, new GameManagerT(vPlayersT, privateOCs, publicOCsT,
                toolCsT, roundTrack, pool, tCtokens, publicRef.indexOf(uuid)));
    }

    public void updateView() {
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
        try {
            Thread.sleep(timeout2);
        } catch (InterruptedException e) {
            Logger.log("Interrupted Exception");
            e.printStackTrace();
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
            if (vPlayer.getWindow() == null) {
                vPlayer.setWindow(a.get(4 * i + rand.nextInt(3)));
                middlewareServer.startGameViewForced(vPlayer.getuUID());
                Logger.log("startgame forced " + vPlayer.getWindow().getName());
            }
            i++;
        }


        try {
            Thread.sleep(timeout4);
        } catch (InterruptedException e) {
            Logger.log("Interrupted Exception");
            e.printStackTrace();
        }


        i = 0;

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

        i = 0;
        a.clear();

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

                if (jump.contains(remotePlayer)&&!privateLeft.contains(remotePlayer)) {
                    jump.remove(remotePlayer);
                }
                else if (active.contains(remotePlayer)) {
                    this.updateView();
                    this.expected = remotePlayer;
                    middlewareServer.enable(remotePlayer);

                    localPlayer.incrementTurn();

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
                        localPlayer.clearUsedTcAndPlacedDice();
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
            upward = true;
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
            middlewareServer.printScore(play.getuUID(), play.getScore());
            if (play.getScore() == points)
                middlewareServer.setWinner(play.getuUID());
        }
        Logger.log("END OF GAME");
    }
}