package server.logic;

import server.network.NetworkServer;
import shared.SharedClientGame;
import shared.SharedServerMatchManager;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class MatchManager extends NetworkServer implements SharedServerMatchManager {

    public static final Integer MAX_ACTIVE_PLAYER_REFS = 250;
    public final Locker safe = Locker.getSafe();
    public List<SharedClientGame> pp2 = new LinkedList<>();
    public List<SharedClientGame> pp3 = new LinkedList<>();
    public List<SharedClientGame> pp4 = new LinkedList<>();
    public Integer waitingPlayer = new Integer(0);
    private static MatchManager instance = null;

    public static MatchManager getInstance() {
        return instance;
    }

    public static void setInstance() {
        if(instance == null) {
            instance = new MatchManager();
        }
    }

    //make the constructor public so that this class cannot be instantiated from outer classes
    public MatchManager() {
        try {
            // Get local IP
            this.serverIp = InetAddress.getLocalHost().getHostAddress();
            // Start RMI registry on this machine
            this.rmiRegistry = LocateRegistry.getRegistry(this.serverIp, RMI_PORT);
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

    public Integer getWaitingPlayer() {
        return waitingPlayer;
    }

    public Integer getMaxActivePlayerRefs() {
        return MAX_ACTIVE_PLAYER_REFS;
    }

    public String startGame(SharedClientGame client, Integer nMates) {

        synchronized (safe.Lock1) {
            waitingPlayer++;
            if (waitingPlayer == MAX_ACTIVE_PLAYER_REFS) {
                return "Too many incoming requests, please try again later. Sorry for that.";
            }
        }
        if (nMates == 2) {
            synchronized (safe.Lock2.get(0)) {
                pp2.add(client);
            }
        } else if (nMates == 3) {
            synchronized (safe.Lock2.get(1)) {
                pp3.add(client);
            }
        } else if (nMates == 4) {
            synchronized (safe.Lock2.get(2)) {
                pp4.add(client);
            }
        }
        return "Connection successful. Please wait for other players to connect";
    }

    public void connect(SharedClientGame c, Integer n) throws RemoteException {
        System.out.println("Someone connected, nMates: " + n.toString());
    }

    /* Local */
    public String getServerIp() {
        return serverIp;
    }
}