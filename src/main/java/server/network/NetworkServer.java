package server.network;

import shared.Logger;
import shared.Network;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class NetworkServer extends Network {
    private static final Integer RMI_OBJECT_PORT = 1100;

    /**
     * This method will call the private constructor only if the attribute {@link server.network.NetworkServer#instance} is not null.
     * This will ensure that there will be just one instance of {@link server.network.NetworkServer}
     */
    public static void setInstance() {
        if(instance == null) {
            instance = new NetworkServer();
        }
    }

    /**
     * This private constructor will avoid the creation of multiple instances of this class; since this class is an extension
     * of {@link shared.Network}, the constructor of the superclass is first called with the port on which export the objects
     * with {@link shared.Network#remotize(Remote)}; then the {@code Registry} attribute must be initialized: in case of the
     * server side, the registry will be created and bound to port {@value shared.Network#RMI_METHOD_PORT} (specified by attribute {@link shared.Network#RMI_METHOD_PORT})
     */
    public NetworkServer() {
        super(RMI_OBJECT_PORT);
        try {
            // Start RMI registry on this machine
            this.rmiRegistry = LocateRegistry.createRegistry(NetworkServer.RMI_METHOD_PORT);
        } catch (RemoteException re) {
            Logger.log("Error in RMI Registry initialization!");
            Logger.strace(re);
        }
    }
}