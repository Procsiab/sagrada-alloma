package Network;

import Network.Server.mainServer;
import Network.Shared.SharedNetwork;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

// The server's implementation of the network interface:
// should implement the shared interface
public class Network implements SharedNetwork {
    public static final Integer RMI_PORT = 1099;
    public static final String RMI_IFACE_NAME = "Network";
    public static final Integer RMI_IFACE_PORT = 1100;
    public static final Integer SOCKET_PORT = 1101;
    private String serverIp;
    private Registry rmiRegistry;

    // The constructor creates an instance for the RMI
    // registry and gets server's LAN IP into local attribute
    public Network(mainServer server) throws RemoteException, UnknownHostException {
        this.rmiRegistry = LocateRegistry.createRegistry(Network.RMI_PORT);
        this.serverIp = InetAddress.getLocalHost().getHostAddress();
        // Export the object listener on specific server port
        UnicastRemoteObject.exportObject(this, RMI_IFACE_PORT);
    }

    public String getServerIp() {
        return serverIp;
    }

    public String getPlayerName(Integer id) {
        return mainServer.getInstance().getP().get(id).getName();
    }

    public String getPlayerRefList() {
        return Arrays.toString(mainServer.getInstance().getpR().toArray());
    }
}