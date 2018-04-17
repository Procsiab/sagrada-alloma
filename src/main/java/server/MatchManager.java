package server;

import server.network.NetworkServer;
import shared.Logic.Locker;
import shared.SharedClientGame;
import shared.SharedServerMatchManager;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MatchManager implements SharedServerMatchManager {

    public static final Integer MAX_ACTIVE_PLAYER_REFS = 250;
    public final Locker safe = Locker.getSafe();
    public List<String> nickNames = new ArrayList<>();
    public LinkedList<SharedClientGame> Q = new LinkedList<>();
    public Integer waitingPlayer = new Integer(0);
    private static MatchManager instance = null;
    public static final Integer RMI_PORT = 1099;
    public static final Integer RMI_IFACE_PORT = 1100;
    public static final Integer SOCKET_PORT = 1101;
    protected String serverIp;
    protected Registry rmiRegistry;

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

    public String startGame(SharedClientGame client) {

        synchronized (safe.SLock1) {
            waitingPlayer++;
            if (waitingPlayer == MAX_ACTIVE_PLAYER_REFS)
                return "Too many incoming requests, please try again later. Sorry for that.";
        }
        synchronized (safe.SLock2) {
            Q.addLast(client);
            notifyAll();
        }
        return "Connection successful. Please wait for other players to connect";
    }

    public boolean exitGame1(SharedClientGame client){
        if(!Q.remove(client)){
            //you may call a function client-side that delete client from its match
            return false;
        }
        synchronized (safe.SLock1){
            waitingPlayer--;
        }
        return true;
    }


    /* Remote */
    public void connect(SharedClientGame c, Integer n) throws RemoteException {
        System.out.println("Someone connected, nMates: " + n.toString());
        c.print("Hi from server");
    }

    /* Local */
    public String getServerIp() {
        return serverIp;
    }

    public boolean setNickName(String nickName) {
        synchronized (safe.SLock3) {
            if (nickNames.contains(nickName))
                return false;
            else
                nickNames.add(nickName);
            return true;
        }
    }
}