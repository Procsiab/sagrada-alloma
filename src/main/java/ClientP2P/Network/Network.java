package ClientP2P.Network;

import ClientP2P.Network.Shared.SharedMainClient;
import ClientP2P.Network.Shared.SharedNetwork;
import ServerP2P.mainServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

// The server's implementation of the network interface:
// should implement the shared interface
public class Network implements SharedNetwork {
    private static final String SERVER_IP = "localhost";
    public static final Integer RMI_PORT = 1099;
    public static final String RMI_IFACE_NAME = "ServerP2P/Network";
    public static final Integer RMI_IFACE_PORT = 1100;
    public static final Integer SOCKET_PORT = 1101;
    private String serverIp;
    private Registry rmiRegistry;

    // The constructor creates an instance for the RMI
    // registry and gets server's LAN IP into local attribute
    public Network() throws RemoteException, UnknownHostException {
        this.rmiRegistry = LocateRegistry.createRegistry(Network.RMI_PORT);
        this.serverIp = InetAddress.getLocalHost().getHostAddress();
        // Inform the registry about symbolic server name
        System.setProperty("java.rmi.server.hostname", SERVER_IP);
        // Setup permissive security policy - yay haxorz come in
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
        // Export the object listener on specific server port
        UnicastRemoteObject.exportObject(this, RMI_IFACE_PORT);
    }

    public String getServerIp() {
        return serverIp;
    }

    public void connect(SharedMainClient c) throws RemoteException {
        // Call corresponding server's method
        mainServer.getInstance().connect(c);
    }

}