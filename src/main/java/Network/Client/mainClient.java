package Network.Client;

import Network.Shared.SharedNetwork;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class mainClient {
    private static final String SERVER_IP = "localhost";
    public static final Integer RMI_PORT = 1099;
    public static final String RMI_IFACE_NAME = "Network";

    public static void main(String [] args) throws RemoteException, NotBoundException {
        // Look for the RMI registry on specific server port
        Registry rmiRegistry = LocateRegistry.getRegistry(SERVER_IP, RMI_PORT);
        // Get a reference to the remote instance of Network, through SaredNetwork interface
        SharedNetwork netIface = (SharedNetwork) rmiRegistry.lookup(RMI_IFACE_NAME);
        //TODO Get these values from system and graphic interface interaction
        String mac = "DE:AD:BE:EF", ip = "1.1.1.1", port = "666", mates = "6", name = "Asdrubale";
        //TODO Send those values to server
    }
}