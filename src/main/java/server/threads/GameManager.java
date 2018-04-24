package server.threads;

import server.*;
import server.abstracts.PrivateOC;
import server.abstracts.PublicOC;
import server.abstracts.ToolC;
import server.network.NetworkServer;
import shared.*;
import shared.logic.GeneralTask;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class GameManager extends GeneralTask implements SharedServerGameManager {

    public final ArrayList<SharedServerPlayer> fixedPlayers;
    public List<SharedClientGame> players;
    private final Integer sleepTime;
    private final Integer nMates;
    private ArrayList<SharedServerPlayer> vPlayers;
    public MatchManager matchManager = MatchManager.getInstance();
    private boolean action = false;
    public static final ReentrantLock lock1 = new ReentrantLock();
    public static final ReentrantLock lock2 = new ReentrantLock();
    public ArrayList<PrivateOC> privateOCs = new ArrayList<>();
    public ArrayList<PublicOC> publicOCs = new ArrayList<>();
    public ArrayList<ToolC> toolCards;
    public ArrayList<SharedClientGame> left = new ArrayList<>();
    public ArrayList<SharedClientGame> unresponsive = new ArrayList<>();
    public ArrayList<SharedClientGame> active = new ArrayList<>();
    public ScoreBoard scoreBoard;
    public boolean check1 = false;
    public ArrayList<Dice> dices = new ArrayList<>();
    public ArrayList<Dice> pool = new ArrayList<>();

    public GameManager(ArrayList<SharedClientGame> players, Integer nMates) {
        Random rand = new Random();
        this.fixedPlayers = (ArrayList<SharedServerPlayer>)vPlayers.clone();
        this.players = players;
        this.sleepTime = 10000;
        this.nMates = nMates;
        int i = 1;

        while (i<=90){
            if(1<=i && i<=18)
                dices.add(new Dice('r', rand.nextInt(6)));
            else if(19<=i && i<=36)
                dices.add(new Dice('y', rand.nextInt(6)));
            else if(37<=i && i<=54)
                dices.add(new Dice('g', rand.nextInt(6)));
            else if(55<=i && i<=72)
                dices.add(new Dice('b', rand.nextInt(6)));
            else if(73<=i && i<=90)
                dices.add(new Dice('p', rand.nextInt(6)));
            i++;
        }
        // Export the reference as UnicastRemoteObject
        NetworkServer.getInstance().remotize(this);
    }

    public void setAction(boolean action) {
        synchronized (lock1) {
            this.action = action;
        }
    }

    public boolean getAction() {
        synchronized (lock1) {
            return action;
        }
    }

    public void setLoser(SharedClientGame loser) {
        this.left.add(loser);
    }

    public void shiftPlayers() {
        SharedClientGame temp;
        temp = players.remove(0);
        players.add(temp);
    }

    public void throwDice(){
        pool.clear();
        int num = 2*active.size()+1;
        int i = 0;

        while(i<num){
            pool.add(dices.remove(0));
            i++;
        }

    }


    public void setToolCards(ArrayList<ToolC> toolCards) {
        this.toolCards = toolCards;
    }

    public Integer score(SharedServerPlayer player){
        Integer score = 0;
        //computation
        return score;
    }

    @Override
    public void run() {

        int i = 0;
        int j = 0;

        for (SharedClientGame client: players) {
            vPlayers.add(new Player(1,this));
        }
        //TODO pass 'this' reference to StartGameController
        for (SharedClientGame client: players) {
            try {
                client.setNetPlayers(vPlayers);
                client.setNPlayer(i);
            } catch (RemoteException re) {
                Logger.log("Error calling method on remote object!");
                Logger.strace(re);
            }
            i++;
        }

        Random rand = new Random();
        ArrayList<Integer> a = new ArrayList<>();

        j = rand.nextInt(5);
        while (i<players.size()) {
            while (a.contains(j)) {
                j = rand.nextInt(5);
            }
            a.add(j);
            i++;
        }
        i = 0;
        while(i<players.size()) {

            try {
                vPlayers.get(i).setPrivateOC(a.get(i));
            } catch (RemoteException re) {
                Logger.log("Error calling method on remote object!");
                Logger.strace(re);
            }
            i++;
        }
        i = 0;

        a.clear();

        j = rand.nextInt(24);
        while (i<4*players.size()) {
            while (a.contains(j)) {
                j = rand.nextInt(24);
            }
            a.add(j);
            i++;
        }
        i = 0;

        while(i<players.size()) {
            //return of this function client-side after client has placed his chosen card
            try {
                players.get(i).chooseWindow(a.subList(((i+1)*4),((i+2)*4-1)));
            } catch (RemoteException re) {
                Logger.log("Error calling method on remote object!");
                Logger.strace(re);
            }

            i++;
        }
        i = 0;

        a.clear();

        j = rand.nextInt(4);
        while (i<players.size()) {
            while (a.contains(j)) {
                j = rand.nextInt(4);
            }
            a.add(j);
            i++;
        }
        i = 0;

        while(i<players.size()) {
            try {
                vPlayers.get(i).setFrame(a.get(i));
            } catch (RemoteException re) {
                Logger.log("Error calling method on remote object!");
                Logger.strace(re);
            }

            i++;
        }
        i = 0;


        for (SharedServerPlayer player: vPlayers) {
            try {
                player.setTokens();
            } catch (RemoteException re) {
                Logger.log("Error calling method on remote object!");
                Logger.strace(re);
            }

            //give token and make the call from client
        }


        i = 0;
        a.clear();

        j = rand.nextInt(12);
        while (i<3) {
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
        while (i<3) {
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


        for (SharedClientGame player : players) {
            //client will get information from netGameManager, and from netPlayers
            try {
                player.updateView();
            } catch (RemoteException re) {
                Logger.log("Error calling method on remote object!");
                Logger.strace(re);
            }

        }


        j = 1;
        i = 1;
        while (j <= 10) {
            while (i <= players.size()) {
                active.trimToSize();
                //check if only one host
                if (active.size() == 1) {
                    try {
                        active.get(active.size()).aPrioriWin();
                    } catch (RemoteException re) {
                        Logger.log("Error calling method on remote object!");
                        Logger.strace(re);
                    }

                    return;
                }
                //check if re/connected
                try {
                    check1 = players.get(i - 1).ping();
                } catch (RemoteException re) {
                    Logger.log("Error calling method on remote object!");
                    Logger.strace(re);
                }


                //check if active and go ahead
                if (active.contains(players.get(i - 1)) && check1) {
                    try {
                        players.get(i - 1).enable();
                    } catch (RemoteException re) {
                        Logger.log("Error calling method on remote object!");
                        Logger.strace(re);
                    }

                    while (this.action == false)
                        try {
                            this.wait(sleepTime);
                            this.action = true;
                            unresponsive.add(players.get(i-1));
                        } catch (InterruptedException ie) {
                            Logger.log("Thread sleep was interrupted!");
                            Logger.strace(ie);
                            Thread.currentThread().interrupt(); //Proper handling of InterruptedException
                        }
                    this.action = false;
                    check1 = false;
                    try {
                        players.get(i - 1).shut();
                    } catch (RemoteException re) {
                        Logger.log("Error calling method on remote object!");
                        Logger.strace(re);
                    }
                }
                i++;
            }

            while (i >= 1) {
                active.trimToSize();
                //check if only one host
                if (active.size() == 1) {
                    try {
                        active.get(active.size()).aPrioriWin();
                    } catch (RemoteException re) {
                        Logger.log("Error calling method on remote object!");
                        Logger.strace(re);
                    }

                    return;
                }
                //check if re/connected
                try {
                    check1 = players.get(i - 1).ping();
                } catch (RemoteException re) {
                    Logger.log("Error calling method on remote object!");
                    Logger.strace(re);
                }


                //check if active and go ahead
                if (active.contains(players.get(i - 1)) && check1) {
                    try {
                        players.get(i - 1).enable();
                    } catch (RemoteException re) {
                        Logger.log("Error calling method on remote object!");
                        Logger.strace(re);
                    }

                    while (this.action == false)
                        try {
                            this.wait(sleepTime);
                            this.action = true;
                        } catch (InterruptedException ie) {
                            Logger.log("Thread sleep was interrupted!");
                            Logger.strace(ie);
                            Thread.currentThread().interrupt(); //Proper handling of InterruptedException
                        }
                    this.action = false;
                    check1 = false;
                    try {
                        players.get(i - 1).shut();
                    } catch (RemoteException re) {
                        Logger.log("Error calling method on remote object!");
                        Logger.strace(re);
                    }

                }
                i--;
            }
            shiftPlayers();
            j++;
        }
        i = 0;
        for (SharedClientGame player: players
             ) {
            try {
                player.printScore(score(vPlayers.get(i)));
            } catch (RemoteException re) {
                Logger.log("Error calling method on remote object!");
                Logger.strace(re);
            }

            i++;
        }
    }
}
