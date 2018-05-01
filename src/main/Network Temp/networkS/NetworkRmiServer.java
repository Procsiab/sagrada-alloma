package server.networkS;

import shared.Logger;
import shared.network.NetworkRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * <h1>NetworkRmi server-side extension</h1>
 * <p>This class will be used in the server context to serve the client requests, through the methods of {@link NetworkRmi}.</p>
 * <p>Moreover, this extension adds to its superclass specification a constructor which initializes the RMI port and the registry.<br>
 *     Finally, the method {@link NetworkRmiServer#setInstance()} is provided, to allow single-time access to
 *     the private constructor, realizing the singleton pattern implementation</p>
 * @see NetworkRmiServer
 * @see NetworkRmi
 */
public class NetworkRmiServer extends NetworkRmi {
    private static final Integer RMI_OBJECT_PORT = 1100;

    /**
     * This method will call the private constructor only if the attribute {@link NetworkRmiServer#instance} is not null.
     * This will ensure that there will be just one instance of {@link NetworkRmiServer}
     */
    public static void setInstance() {
        if(instance == null) {
            instance = new NetworkRmiServer();
        }
    }

    /**
     * This private constructor will avoid the creation of multiple instances of this class; since this class is an extension
     * of {@link NetworkRmi}, the constructor of the superclass is first called with the port on which export the objects
     * with {@link NetworkRmi#remotize(Remote)}; then the {@code Registry} attribute must be initialized: in case of the
     * server side, the registry will be created and bound to port {@value NetworkRmi#RMI_METHOD_PORT} (specified by attribute {@link NetworkRmi#RMI_METHOD_PORT})
     */
    private NetworkRmiServer() {
        super(RMI_OBJECT_PORT);
        try {
            // Start RMI registry on this machine
            this.rmiRegistry = LocateRegistry.createRegistry(NetworkRmiServer.RMI_METHOD_PORT);
        } catch (RemoteException re) {
            Logger.log("Error in RMI Registry initialization!");
            Logger.strace(re);
        }
    }
}