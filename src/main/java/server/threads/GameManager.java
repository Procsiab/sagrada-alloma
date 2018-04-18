package server.threads;

import server.*;
import server.abstracts.PrivateOC;
import server.abstracts.PublicOC;
import server.abstracts.ToolC;
import shared.*;
import shared.Logic.GeneralTask;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class GameManager extends GeneralTask implements SharedServerGameManager {
    /*************************************************/
    public static final Integer RMI_PORT = 1099;
    public static final Integer RMI_IFACE_PORT = 1100;
    public static final Integer SOCKET_PORT = 1101;
    protected String serverIp;
    protected Registry rmiRegistry;
    /*************************************************/


    public final ArrayList<SharedClientGame> fixedPlayers;
    public List<SharedClientGame> players;
    private final Integer sleepTime;
    private final Integer nMates;
    private ArrayList<Player> vPlayers;
    public MatchManager matchManager = MatchManager.getInstance();
    private boolean action = false;
    public static final ReentrantLock Lock1 = new ReentrantLock();
    public static final ReentrantLock Lock2 = new ReentrantLock();
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
        this.fixedPlayers = (ArrayList)players.clone();
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

        try {
            // Start RMI registry on this machine
            this.rmiRegistry = LocateRegistry.getRegistry(MatchManager.getInstance().getServerIp(), RMI_PORT);
            // Inform the registry about symbolic server name
            System.setProperty("java.rmi.server.hostname", this.serverIp);
            // Setup permissive security policy
            System.setProperty("java.rmi.server.useCodebaseOnly", "false");
            // Export the object listener on specific server port
            UnicastRemoteObject.exportObject(this, RMI_IFACE_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAction(boolean action) {
        synchronized (Lock1) {
            this.action = action;
        }
    }

    public boolean getAction() {
        synchronized (Lock1) {
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

    public Integer score(Player player){
        Integer score = 0;
        //computation
        return score;
    }

    @Override
    public void run() {

        int i = 0;
        int j = 0;

        for (SharedClientGame client: players
             ) {
            vPlayers.add(new Player(1,this));
        }

        for (SharedClientGame client: players) {
            client.setNetPlayers(vPlayers);
            client.setNPlayer(i);
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
            vPlayers.get(i).setPrivateOC(a.get(i));
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
            players.get(i).chooseWindow(a.subList(((i+1)*4),((i+2)*4-1)));
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
            vPlayers.get(i).setFrame(a.get(i));
            i++;
        }
        i = 0;


        for (Player player: vPlayers) {
            player.setTokens();
            //give token and make the call from client
        }

        scoreBoard = new ScoreBoard(fixedPlayers);

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
        toolCards.add(matchManager.toolCS.get(a.get(1)));

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
            player.updateView();
        }


        j = 1;
        i = 1;
        while (j <= 10) {
            while (i <= players.size()) {
                active.trimToSize();
                //check if only one host
                if (active.size() == 1) {
                    active.get(active.size()).aPrioriWin();
                    return;
                }
                //check if re/connected
                try {
                    check1 = players.get(i - 1).ping();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //check if active and go ahead
                if (active.contains(players.get(i - 1)) && check1) {
                    players.get(i - 1).enable();
                    while (this.action == false)
                        try {
                            this.wait(sleepTime);
                            this.action = true;
                            unresponsive.add(players.get(i-1));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    this.action = false;
                    check1 = false;
                    players.get(i - 1).shut();
                }
                i++;
            }

            while (i >= 1) {
                active.trimToSize();
                //check if only one host
                if (active.size() == 1) {
                    active.get(active.size()).aPrioriWin();
                    return;
                }
                //check if re/connected
                try {
                    check1 = players.get(i - 1).ping();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //check if active and go ahead
                if (active.contains(players.get(i - 1)) && check1) {
                    players.get(i - 1).enable();
                    while (this.action == false)
                        try {
                            this.wait(sleepTime);
                            this.action = true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    this.action = false;
                    check1 = false;
                    players.get(i - 1).shut();
                }
                i--;
            }
            shiftPlayers();
            j++;
        }
        i = 0;
        for (SharedClientGame player: players
             ) {
            player.score(score(vPlayers.get(i)));
            i++;
        }
    }
}
