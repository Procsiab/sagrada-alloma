package shared.network;

import client.network.NetworkRmiClient;
import server.network.NetworkRmiServer;
import shared.Logger;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * <h1>NetworkRmi Abstract Class</h1>
 * <p>This class will hold general network parameters and methods, to be shared between client and server.</p>
 * <p>Specific extensions of this class are {@link NetworkRmiClient} and {@link NetworkRmiServer};<br>
 *     both server and client extensions will add custom methods to manage RMI and Socket specific connectivity requirements</p>
 * @see NetworkRmiServer
 * @see NetworkRmiClient
 */
public abstract class NetworkRmi implements Connection {
    public static final Integer RMI_METHOD_PORT = 1099;

    protected Registry rmiRegistry;
    protected static Connection instance = null;
    private String IP;
    private Integer rmiObjectPort;

    /**
     * This method is used to obtain a reference to the singleton class; this will be possible only if {@link NetworkRmiServer#setInstance()}
     * or {@link NetworkRmiServer#setInstance()} have been called before, otherwise a {@code null} pointer will be returned
     * @return NetworkRmi Returns an instance of an implementation of the {@link NetworkRmi} abstract class, as stated
     * in the class description
     */
    public static Connection getInstance() {
        return instance;
    }

    /**
     * This method is used to obtain a reference to the class attribute {@link NetworkRmi#rmiRegistry}; this will be
     * possible only if {@link NetworkRmiServer#setInstance()} or {@link NetworkRmiServer#setInstance()} have been called before,
     * otherwise a {@code null} pointer will be returned
     * @return Registry Returns an instance of the RMI registry initialized in the class
     */
    public Registry getRmiRegistry() {
        return this.rmiRegistry;
    }

    /**
     * This method is used to return the machine's IP over the local network
     * @return String Returns the machine's IP address encoded as a string
     */
    public String getIp() {
        return this.IP;
    }

    /**
     * This is the constructor that will be used by the extension of {@link NetworkRmi} abstract class; this method
     * will initialize all static and instance attributes except for {@link NetworkRmi#rmiRegistry}.
     * It is necessary to handle a {@code RemoteException} when initializing a {@code Registry} object.
     * @param port The port number to bind RMI listeners: use {@code 0} to use a random port, chosen by the OS
     *             (recommended on the client side)
     */
    public NetworkRmi(Integer port) {
        try {
            // Set the port to export RMI objects
            this.rmiObjectPort = port;
            // Get local IP
            this.IP = InetAddress.getLocalHost().getHostAddress();
            // Inform the registry about server's address
            System.setProperty("java.rmi.server.hostname", this.IP);
            // Setup permissive security policy
            System.setProperty("java.rmi.server.useCodebaseOnly", "false");
        } catch (UnknownHostException uhe) {
            Logger.log("Unable to resolve local host name/address!");
            Logger.strace(uhe);
        }
    }

    /**
     * This method exports an object, which must extend {@code Remote} interface, using the default port {@link NetworkRmi#rmiObjectPort}
     * provided by the construtor
     * @param o Reference to the object you would like to export over RMI
     */
    public void remotize(Remote o) {
        try {
            UnicastRemoteObject.exportObject(o, this.rmiObjectPort);
        } catch (RemoteException re) {
            Logger.log("Error exporting with UnicastRemoteObject!");
            Logger.strace(re);
        }
    }

    /**
     * This method returns a reference to an object bound to a specific name in the registry referenced by {@link NetworkRmi#rmiRegistry}.
     * The return type is automatically cast to the type of the object assigned to this method, for example: assume that
     * {@code SharedInterface} is an interface owned by both client and server; assume that on the server an object which
     * implements that interface has been exported, and also the client has got an extension of {@link NetworkRmi} class,
     * already initialized; then the following assignment is valid
     * <p>{@code SharedInterface remoteRef = ImplementedNetwork.getInstance().getExportedObject("remoteRefName")}<p/>
     * In the example above, this method will return a {@code SharedInterface} object (the type of the generic is inferred
     * by the compiler), if there aren't any exceptions; otherwise the exception will be handled inside the method
     * @param boundName Name bound to the object in the registry, that will be a String object
     * @return Object Returns a reference to an object which extends {@code Remote}, and cast it to the type specified
     * for the generic (or inferred by the compiler)
     */
    public <T extends Remote> T getExportedObject(String boundName) {
        T exportedObject = null;
        try {
            exportedObject = (T) this.rmiRegistry.lookup(boundName);
        } catch (NotBoundException nbe) {
            Logger.log("Error in lookup for name  " + boundName + " in RMI Registry: maybe is not bound!");
        } catch (RemoteException re) {
            Logger.log("Error retrieving " + boundName + " from RMI Registry!");
            Logger.strace(re);
        } catch (ClassCastException cce) {
            Logger.log("Error casting Remote object into destination class!");
        }
        return exportedObject;
    }


    /**
     * This method binds a given object to specified a name ({@code String}), on the registry in the attribute {@link NetworkRmi#rmiRegistry}.
     * It is necessary to allow an object to be exported using {@link NetworkRmi#remotize(Remote)} method, and also
     * the exported object must implement a shared interface which extends {@code remote}
     * @param o Object to export, which should implement {@code Remote} or an interface which extends it
     * @param n Name of the object on the registry, should be a {@code String} known to both client and server side
     */
    public void export(Remote o, String n) {
        // Format an URL string to be used in RMI registry
        String rmiUrl = "//" + this.getIp() + ":" + RMI_METHOD_PORT.toString() + "/" + n;
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
}