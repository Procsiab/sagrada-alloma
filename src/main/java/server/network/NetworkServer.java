package server.network;

import server.MainServer;
import server.logic.MatchManager;
import shared.SharedNetworkClient;
import shared.SharedNetworkServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

// The server's implementation of the network interface:
// should implement the shared interface
public class NetworkServer implements SharedNetworkServer {
    public static final Integer RMI_PORT = 1099;
    public static final String RMI_IFACE_NAME = "NetworkServer";
    public static final Integer RMI_IFACE_PORT = 1100;
    public static final Integer SOCKET_PORT = 1101;
    public String serverIp;
    public Registry rmiRegistry;
    private static NetworkServer instance = null;

    public static NetworkServer getInstance() {
        return instance;
    }

    public static void setInstance() {
        if(instance == null) {
            instance = new NetworkServer();
        }
    }

    // The constructor creates an instance for the RMI
    // registry and gets server's LAN IP into local attribute
    private NetworkServer() {
        try {
            // Start RMI registry on this machine
            this.rmiRegistry = LocateRegistry.createRegistry(NetworkServer.RMI_PORT);
            // Get local IP
            this.serverIp = InetAddress.getLocalHost().getHostAddress();
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

    /* Local */
    public void connect(SharedNetworkClient c, Integer n) throws RemoteException {
        MainServer.getInstance().connect(c, n);
    }

    /* Local */
    public String getServerIp() {
        return serverIp;
    }

    /* Local */
    public String startGame(SharedNetworkClient c, Integer nMates) {
        // Call corresponding server's method
        if(MatchManager.getInstance().startGame(c,nMates)) {
            return "Connection successful. Please wait for other players to connect";
        }
        return "Too many incoming requests, please try again later. Sorry for that.";
    }

    /* Remote */
    public void printMessage(SharedNetworkClient c, String s) throws RemoteException {
        c.printMessage(s);
    }

}