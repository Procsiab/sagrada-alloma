package client.network;

import shared.Logger;
import shared.Network;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class NetworkClient extends Network {
    public static final String SERVER_ADDRESS = "localhost";
    private static final Integer RMI_OBJECT_PORT = 0;

    /**
     * This method will call the private constructor only if the attribute {@link client.network.NetworkClient#instance} is not null.
     * This will ensure that there will be just one instance of {@link client.network.NetworkClient}
     */
    public static void setInstance() {
        if(instance == null) {
            instance = new NetworkClient();
        }
    }

    /**
     * This private constructor will avoid the creation of multiple instances of this class; since this class is an extension
     * of {@link shared.Network}, the constructor of the superclass is first called with the port on which export the objects
     * with {@link shared.Network#remotize(Remote)}; then the {@code Registry} attribute must be initialized: in case of the
     * client side, the registry will be located on the local network, using {@link client.network.NetworkClient#SERVER_ADDRESS} attribute
     */
    private NetworkClient() {
        super(RMI_OBJECT_PORT);
        try {
            // Obtain RMI registry reference from server
            rmiRegistry = LocateRegistry.getRegistry(SERVER_ADDRESS, RMI_METHOD_PORT);
        } catch (RemoteException re) {
            Logger.log("Error in RMI Registry connection! (server: " + SERVER_ADDRESS + ")");
            Logger.strace(re);
        }
    }
}