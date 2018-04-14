package client.threads;

import client.gui.StartGameController;
import shared.*;
import shared.Logic.GeneralTask;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends GeneralTask implements SharedClientGame {

    private SharedServerMatchManager netMatchManager;
    private SharedServerGameManager netGameManager;
    private Integer nMates;
    private ReentrantLock Lock1 = new ReentrantLock();
    public static final String SERVER_IP = "localhost";
    public static final Integer RMI_PORT = 1099;
    public static final String RMI_IFACE_NAME = "NetworkServer";
    public static final Integer RMI_IFACE_PORT = 1100;
    public static final Integer SOCKET_PORT = 1101;
    protected String clientIp;
    protected Registry rmiRegistry;
    private StartGameController graphics;

    private ArrayList<Player> players;
    private Match match;
    private String Message1;

    public Game() {
        try {
            // Look for the RMI registry on specific server port
            this.rmiRegistry = LocateRegistry.getRegistry(SERVER_IP, RMI_PORT);
            // get local IP
            this.clientIp = InetAddress.getLocalHost().getHostAddress();
            // Get a reference to the remote instance of client, through shared interface
            this.netMatchManager = (SharedServerMatchManager) rmiRegistry.lookup(RMI_IFACE_NAME);

            // Inform the registry about symbolic server name
            System.setProperty("java.rmi.server.hostname", this.clientIp);
            // Setup permissive security policy
            System.setProperty("java.rmi.server.useCodebaseOnly", "false");
            // Export the object listener on specific server port
            UnicastRemoteObject.exportObject(this, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setnMates(Integer nMates) {
        this.nMates = nMates;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setGraphics(StartGameController graphics) {
        this.graphics = graphics;
    }

    @Override
    public void run() {
        super.run();

        synchronized (Lock1){
            while (nMates == null)
                try {
                    Lock1.wait();
                }catch (InterruptedException e){
                e.printStackTrace();
                }
        }
        try {
            Message1 = netMatchManager.startGame(this, nMates);
        } catch (Exception e ){
            e.printStackTrace();
        }
    }



    public String getClientIp() {
        return this.clientIp;
    }
}