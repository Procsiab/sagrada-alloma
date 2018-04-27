package client.network;

import server.network.NetworkRmiServer;
import shared.Logger;
import shared.network.NetworkRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * <h1>NetworkRmi client-side extension</h1>
 * <p>This class will be used in the client context to communicate with the server, by using the methods of {@link NetworkRmi}.</p>
 * <p>Moreover, this extension adds to its superclass specification a constructor which initializes the RMI port and the registry.<br>
 *     Finally, the method {@link NetworkRmiClient#setInstance()} is provided, to allow single-time access to
 *     the private constructor, realizing the singleton pattern implementation</p>
 * @see NetworkRmiServer
 * @see NetworkRmi
 */
public class NetworkRmiClient extends NetworkRmi {
    public static final String SERVER_ADDRESS = "localhost";
    private static final Integer RMI_OBJECT_PORT = 0;
    /**
     * This method will call the private constructor only if the attribute {@link NetworkRmiClient#instance} is not null.
     * This will ensure that there will be just one instance of {@link NetworkRmiClient}
     */
    public static void setInstance() {
        if(instance == null) {
            instance = new NetworkRmiClient();
        }
    }

    /**
     * This private constructor will avoid the creation of multiple instances of this class; since this class is an extension
     * of {@link NetworkRmi}, the constructor of the superclass is first called with the port on which export the objects
     * with {@link NetworkRmi#remotize(Remote)}; then the {@code Registry} attribute must be initialized: in case of the
     * client side, the registry will be located on the local network, using {@link NetworkRmiClient#SERVER_ADDRESS} attribute
     */
    private NetworkRmiClient() {
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