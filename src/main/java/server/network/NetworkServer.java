package server.network;

import shared.Logger;
import shared.Network;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

//TODO Add socket support, using these method signatures
public class NetworkServer extends Network {
    private String serverIp;
    private Registry rmiRegistry;
    private static NetworkServer instance = null;

    /**
     * Obtain a reference to this class' instance
     * @return NetworkServer
     */
    public static NetworkServer getInstance() {
        return instance;
    }

    /**
     * Generate the singleton instance for this class
     */
    public static void setInstance() {
        if(instance == null) {
            instance = new NetworkServer();
        }
    }

    /**
     * Get the RMI registry instance on server
     * @return Registry
     */
    public Registry getRmiRegistry() {
        return this.rmiRegistry;
    }

    /**
     * Obtain server's local IP address
     * @return String
     */
    public String getServerIp() {
        return serverIp;
    }

    public NetworkServer() {
        super();
        try {
            // Get local IP
            this.serverIp = InetAddress.getLocalHost().getHostAddress();
            // Inform the registry about server's address
            System.setProperty("java.rmi.server.hostname", this.serverIp);
            // Setup permissive security policy
            System.setProperty("java.rmi.server.useCodebaseOnly", "false");
            // Start RMI registry on this machine
            rmiRegistry = LocateRegistry.createRegistry(NetworkServer.RMI_PORT);
        } catch (UnknownHostException uhe) {
            Logger.log("Unable to resolve local host name/address!");
            Logger.strace(uhe);
        } catch (RemoteException re) {
            Logger.log("Error in RMI Registry initialization!");
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
     * Binds given object to specified name, on internal registry
     * @param o Object to export
     * @param n Name of the object on the registry
     */
    public void export(Remote o, String n) {
        // Format an URL string to be used in RMI registry
        String rmiUrl = "//" + this.serverIp + ":" + RMI_PORT.toString() + "/" + n;
        try {
            // Bind the interface to that symbolic URL in the RMI registry
            Naming.rebind(rmiUrl, o);
        } catch (RemoteException re) {
            Logger.log("Error binding " + n + " in RMI Registry!");
            Logger.strace(re);
        } catch (MalformedURLException mue) {
            Logger.log("Error in URL formatting: " + rmiUrl);
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