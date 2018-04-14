package server.threads;

import server.MatchManager;
import shared.Logic.GeneralTask;
import shared.SharedClientGame;
import shared.SharedServerGameManager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class GameManager extends GeneralTask implements SharedServerGameManager {
    /*************************************************/
    public static final Integer RMI_PORT = 1099;
    public static final Integer RMI_IFACE_PORT = 1100;
    public static final Integer SOCKET_PORT = 1101;
    protected String serverIp;
    protected Registry rmiRegistry;
    /*************************************************/
    private final List<SharedClientGame> players;
    private final Integer sleepTime;
    private boolean action = false;
    private SharedClientGame loser = null;
    public static final ReentrantLock Lock1 = new ReentrantLock();
    public static final ReentrantLock Lock2 = new ReentrantLock();

    public GameManager(ArrayList<SharedClientGame> players) {
        this.players = players;
        this.sleepTime = 10000;
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
        synchronized (Lock2) {
            this.loser = loser;
        }
    }

    public SharedClientGame getLoser() {
        synchronized (Lock2) {
            return loser;
        }
    }

    public void enable(SharedClientGame player) {
        //enable selected player, by invoking method on the client-side. Shut all others.
    }

    public void shiftPlayers() {
        SharedClientGame temp;
        temp = players.remove(0);
        players.add(temp);
    }

    @Override
    public void run() {

        //show shuffle public Objective Card animation on all clients
        //show 4 public objective cards and 1 window frame player board. Player will choose only 1 card.
        //give each player the number of favor Tokens indicated on their card
        //give each player the appropriate score marker
        //place 3 tool cards in the center face up
        //place 3 public obj cards in the center face up
        //first player is the player.get(0)

        int j = 1;
        int i = 1;
        while (j <= 10) {
            while (i <= players.size()) {// not need to sync players as they are a copy and not accessed elsewhere
                synchronized (Lock1) {
                    enable(players.get(i - 1));
                    while (this.action == false)
                        try {
                            this.wait(sleepTime);
                            this.action = true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    this.action = false;
                }
                synchronized (Lock2) {
                    if (this.loser == null) {
                        //print on client's screen who is the loser and close game
                        //enabled with passaturno called from client, not needed in other invocations
                    }
                }
                i++;
            }

            while (i >= 1) {
                while (this.getAction() == false)
                    synchronized (Lock1) {
                        enable(players.get(i - 1));
                        while (this.action == false)
                            try {
                                this.wait(sleepTime);
                                this.action = true;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        this.action = false;
                    }
                synchronized (Lock2) {
                    if (this.loser == null) {
                        //print on client's screen who is the loser and close game
                        //enabled with passaturno called from client, not needed in other invocations
                    }
                }
                i--;
            }
            shiftPlayers();
            j++;
        }
        //scoring phase then call a method each player giving the score
    }
}
