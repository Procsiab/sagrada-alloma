package client.network;

import shared.Logger;
import shared.Network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

//TODO Add socket support, using these method signatures
public class NetworkClient extends Network {
    public static final String SERVER_ADDRESS = "localhost";
    private Registry rmiRegistry;
    private static NetworkClient instance = null;

    /**
     * Obtain a reference to this class' instance
     * @return NetworkServer
     */
    public static NetworkClient getInstance() {
        return instance;
    }

    /**
     * Generate the singleton instance for this class
     */
    public static void setInstance() {
        if(instance == null) {
            instance = new NetworkClient();
        }
    }

    /**
     * Get the RMI registry instance on server
     * @return Registry
     */
    public Registry getRmiRegistry() {
        return this.rmiRegistry;
    }

    public NetworkClient() {
        try {
            // Get local IP
            String myIp = InetAddress.getLocalHost().getHostAddress();
            // Inform the registry about client's address
            System.setProperty("java.rmi.server.hostname", myIp);
            // Setup permissive security policy
            System.setProperty("java.rmi.server.useCodebaseOnly", "false");
            // Obtain RMI registry reference from server
            rmiRegistry = LocateRegistry.getRegistry(SERVER_ADDRESS, RMI_PORT);
        } catch (UnknownHostException uhe) {
            Logger.log("Unable to resolve local host name/address!");
            Logger.strace(uhe);
        } catch (RemoteException re) {
            Logger.log("Error in RMI Registry connection! (server: " + SERVER_ADDRESS + ")");
            Logger.strace(re);
        }
    }

    /**
     * Prepare automatically the object reference to be used with RMI
     * @param o Reference you would like to extend over RMI
     */
    public void remotize(Remote o) {
        try {
            UnicastRemoteObject.exportObject(o, RMI_IFACE_PORT);
        } catch (RemoteException re) {
            Logger.log("Error exporting with UnicastRemoteObject on port "
                    + RMI_IFACE_PORT.toString() + "!");
            Logger.strace(re);
        }
    }

    /**
     * Obtain a reference of an object bound to a name in the internal registry
     * @param n Name bound to the object in the registry
     * @return Object
     */
    public Object getExportedObject(String n) {
        Object exObj = null;
        try {
            exObj = this.rmiRegistry.lookup(n);
        } catch (NotBoundException nbe) {
            Logger.log("Error in lookup for name  " + n + " in RMI Registry: maybe is not bound!");
        } catch (RemoteException re) {
            Logger.log("Error retrieving " + n + " from RMI Registry!");
            Logger.strace(re);
        }
        return exObj;
    }
}