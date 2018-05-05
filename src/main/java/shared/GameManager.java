package shared;

import server.*;
import server.abstracts.PrivateOC;
import server.abstracts.PublicOC;
import server.abstracts.ToolC;
import shared.logic.GeneralTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class GameManager extends GeneralTask implements Serializable {

    public ArrayList<String> publicRef = new ArrayList<>();
    transient public MiddlewareServer middlewareServer = MiddlewareServer.getInstance();
    public ArrayList<String> players = new ArrayList<>();
    public ArrayList<String> players2 = new ArrayList<>();
    private final Integer sleepTime;
    private final Integer nMates;
    private ArrayList<Player> vPlayers;
    public MatchManager matchManager = MatchManager.getInstance();
    private boolean action = false;
    public static final ReentrantLock lock1 = new ReentrantLock();
    public static final ReentrantLock lock2 = new ReentrantLock();
    public ArrayList<PrivateOC> privateOCs = new ArrayList<>();
    public ArrayList<PublicOC> publicOCs = new ArrayList<>();
    public ArrayList<ToolC> toolCards;
    public ArrayList<String> privateLeft = new ArrayList<>();
    public ArrayList<String> unresponsive = new ArrayList<>();
    public ArrayList<String> active = new ArrayList<>();
    public String expected;
    public RoundTrack roundTrack;
    public boolean check1 = false;
    //public Dice drafted;
    public ArrayList<Dice> dices = new ArrayList<>();
    public ArrayList<Dice> pool = new ArrayList<>();

    public GameManager(ArrayList<String> players) {
        Random rand = new Random();
        this.publicRef.addAll(players);
        this.players.addAll(players);
        this.sleepTime = 10000;
        this.nMates = players.size();
        int i = 1;

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

    public void shiftPlayers() {
        String temp;
        temp = players.remove(0);
        players.add(temp);
    }

    public void throwDice() {
        pool.clear();
        int num = 2 * active.size() + 1;
        int i = 0;

        while (i < num) {
            pool.add(dices.remove(0));
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

    public Integer score(Player player) {
        Integer score = 0;
        //computation
        player.setScore(score);
        return score;
    }

    @Override
    public void run() {

        int i = 0;
        int j = 0;

        for (String client : players) {
            vPlayers.add(new Player(1, this, publicRef.get(i)));
        }


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

        while (i < players.size()) {
            Integer k;
            k = middlewareServer.chooseWindow(players.get(i), (ArrayList<Integer>) a.subList(((i + 1) * 4), ((i + 2) * 4 - 1)));
            vPlayers.get(i).setWindow(k);

            i++;
        }
        i = 0;

        a.clear();

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
            vPlayers.get(i).setFrame(a.get(i));
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

        toolCards.add(matchManager.toolCS.get(a.get(0)));
        toolCards.add(matchManager.toolCS.get(a.get(1)));
        toolCards.add(matchManager.toolCS.get(a.get(2)));

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


        for (String player : players) {
            middlewareServer.updateView(player, this);
        }


        j = 1;
        i = 1;
        while (j <= 10) {
            //fitness(left);
            players2.clear();
            players2.addAll(players);
            players2.removeAll(privateLeft);
            //fitness(players2);
            //set nPlayers (which will not change over one turn) for nDices ecc..


            while (i <= players2.size()) {

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

                //check if global blackout
                while (active.size() == 0) {
                    //safe.wait(timeout2);
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
                }

                //check if only one guest
                while (active.size() + unresponsive.size() == 1) {
                    while (active.size() == 0 && unresponsive.size() == 1) {
                        //safe.wait(timeout2);
                        if (privateLeft.contains(players2.get(0))) {
                            unresponsive.clear();
                            players2.clear();
                            //fuck off
                            return;
                        }

                        if (middlewareServer.ping(players2.get(0)))
                            active.add(players2.get(0));
                        unresponsive.clear();

                    }
                    if (active.size() == 1 && unresponsive.size() == 0) {

                        if (middlewareServer.ping(active.get(0))) {
                            middlewareServer.aPrioriWin(active.get(0));
                            return;
                        }
                        //why don't change ISP?
                    }

                }

                //check if active and go ahead
                if (active.contains(players.get(i - 1))) {
                    middlewareServer.updateView(players.get(i - 1), this);
                    this.expected = players.get(i - 1);
                    middlewareServer.enable(players.get(i - 1));


                    while (this.action == false)
                        try {
                            this.wait(sleepTime);
                            this.action = true;
                        } catch (InterruptedException ie) {
                            Logger.log("Thread sleep was interrupted!");
                            Logger.strace(ie);
                            Thread.currentThread().interrupt();
                        }
                    this.action = false;
                    this.expected = null;
                    //middlewareServer.shut(players.get(i - 1));
                }
                i++;
            }

            while (i >= 1) {
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

                //check if global blackout
                while (active.size() == 0) {
                    //safe.wait(timeout2);
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
                }

                //check if only one guest
                while (active.size() + unresponsive.size() == 1) {
                    while (active.size() == 0 && unresponsive.size() == 1) {
                        //safe.wait(timeout2);
                        if (privateLeft.contains(players2.get(0))) {
                            unresponsive.clear();
                            players2.clear();
                            //fuck off
                            return;
                        }

                        if (middlewareServer.ping(players2.get(0)))
                            active.add(players2.get(0));
                        unresponsive.clear();

                    }
                    if (active.size() == 1 && unresponsive.size() == 0) {

                        if (middlewareServer.ping(active.get(0))) {
                            middlewareServer.aPrioriWin(active.get(0));
                            return;
                        }
                        //why don't change ISP?
                    }

                }

                //check if active and go ahead
                if (active.contains(players.get(i - 1))) {
                    middlewareServer.updateView(players.get(i - 1), this);
                    this.expected = players.get(i - 1);
                    middlewareServer.enable(players.get(i - 1));


                    while (this.action == false)
                        try {
                            this.wait(sleepTime);
                            this.action = true;
                        } catch (InterruptedException ie) {
                            Logger.log("Thread sleep was interrupted!");
                            Logger.strace(ie);
                            Thread.currentThread().interrupt();
                        }
                    this.action = false;
                    this.expected = null;
                    //middlewareServer.shut(players.get(i - 1));
                }
                i--;

            }
            shiftPlayers();
            j++;
        }
        i = 0;
        int points = 0;
        int temp;
        for (Player player : vPlayers
                ) {
            temp = score(vPlayers.get(i));
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
    }
}