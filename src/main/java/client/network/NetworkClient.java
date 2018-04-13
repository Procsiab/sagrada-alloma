package client.network;

import client.MainClient;
import client.threads.Game;
import shared.SharedNetworkClient;
import shared.SharedNetworkServer;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class NetworkClient implements SharedNetworkClient {
    public static final String SERVER_IP = "localhost";
    public static final Integer RMI_PORT = 1099;
    public static final String RMI_IFACE_NAME = "NetworkServer";
    public static final Integer RMI_IFACE_PORT = 1100;
    public static final Integer SOCKET_PORT = 1101;
    private static String clientIp;
    private static Registry rmiRegistry;
    private static SharedNetworkServer netServer;
    private static NetworkClient instance = null;
    private Game game;


    public static NetworkClient getInstance() {
        return instance;
    }

    public static void setInstance() {
        if(instance == null) {
            instance = new NetworkClient();
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private NetworkClient() {
        try {
            // Look for the RMI registry on specific server port
            this.rmiRegistry = LocateRegistry.getRegistry(SERVER_IP, RMI_PORT);
            // get local IP
            this.clientIp = InetAddress.getLocalHost().getHostAddress();
            // Get a reference to the remote instance of NetworkClient, through SharedNetworkServer interface
            this.netServer = (SharedNetworkServer) rmiRegistry.lookup(RMI_IFACE_NAME);

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

    /* Local */
    public void printMessage(String s) {
        MainClient.printMessage(s);
    }

    /* Local */
    public String getClientIp() {
        return clientIp;
    }

    /* Remote */
    public void connect(Integer nMates) throws RemoteException {
        netServer.connect(instance, nMates);
    }
}
