package shared.network.rmi;

import shared.*;
import shared.network.*;

import java.net.MalformedURLException;
import java.net.SocketException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * <h1>Network RMI</h1>
 * <p>This class implements {@code Connection} and all its methods, using the Remote Method Invocation networking to set
 * up the connection components.</p><br>
 * <p>Using the constructor with no parameters or just an {@code Integer} representing the RMI port, the instance will act
 * as a server, creating also an RMI registry on teh same machine;</p>
 * <p>using the constructor with only a {@code String} parameter representing the server's hostname, or also with an
 * {@code Integer} parameter representing the server's inbound port, the instance will act as a client, trying to connect
 * to the specified hostname.</p><br>
 * <p>To choose the server's address from the outside (even from user input) a static setter is provided (
 * {@link NetworkRmi#setServerAddress(String)}); is <b>must</b> be used before calling the constructor, otherwise the
 * server will be started on localhost</p>
 * @see SharedProxyServer
 * @see SharedProxyClient
 * @see Connection
 */
public class NetworkRmi implements Connection {
    private static final Integer RMI_METHOD_PORT = 1099;
    private static final Router router = new MethodRouter();

    private static String serverAddress;
    private Registry rmiRegistry;
    private String ip;
    private Integer rmiObjectPort;

    /**
     * Setter for the server address, that must be called before instantiating the class, to provide a different hostname
     * than localhost
     * @param address an IPv4 address or a DNS hostname for the machine which has the RMI registry running on it
     */
    public static void setServerAddress(String address) {
        serverAddress = address;
    }

    /**
     * This method is an helper which should be called from teh constructor: it sets up attributes that are common to
     * the client and the server in an RMI context
     * @param port the port on which the registry will listen on
     */
    private void startRegistrySetup(Integer port) {
        try {
            // Set the port to export RMI objects
            this.rmiObjectPort = port;
            // Get local ip
            this.ip = Connection.getLocalIp("wl");
            // Setup permissive security policy
            System.setProperty("java.rmi.server.useCodebaseOnly", "false");
        } catch (SocketException se) {
            Logger.log("Unable to resolve local host name/address!");
        }
    }

    /**
     * Constructor for the <b>server</b> instance
     * @param port the listening port for incoming connections
     */
    public NetworkRmi(Integer port) {
        startRegistrySetup(port);
        if (serverAddress == null) {
            serverAddress = this.ip;
        }
        Logger.log("RMI registry hostname: " + serverAddress);
        // Inform the registry about server's address
        System.setProperty("java.rmi.server.hostname", serverAddress);
        try {
            // Start RMI registry on this machine
            this.rmiRegistry = LocateRegistry.createRegistry(port);
        } catch (RemoteException re) {
            Logger.log("Error in RMI Registry initialization!");
        }
    }

    /**
     * Constructor for the <b>client</b> instance
     * @param server the server's address or hostname
     * @param port the server's port for accepting the client's socket
     */
    public NetworkRmi(String server, Integer port) {
        startRegistrySetup(port);
        serverAddress = server;
        // Inform the registry about client's address
        System.setProperty("java.rmi.server.hostname", this.ip);
        try {
            // Obtain RMI registry reference from server
            if (server.equals("")) {
                rmiRegistry = LocateRegistry.getRegistry("localhost", port);
            } else {
                rmiRegistry = LocateRegistry.getRegistry(server, port);
            }
        } catch (RemoteException re) {
            Logger.log("Error in RMI Registry connection! (server: " + server + ")");
        }
    }

    /**
     * Constructor for the <b>server</b> instance
     */
    public NetworkRmi() {
        this(RMI_METHOD_PORT);
    }

    /**
     * Constructor for the <b>client</b> instance
     * @param server the server's address or hostname
     */
    public NetworkRmi(String server) {
        this(server, RMI_METHOD_PORT);
    }

    @Override
    public String getLocalIp() {
        return this.ip;
    }

    @Override
    public Integer getListeningPort() {
        return this.rmiObjectPort;
    }

    /**
     * This method prepare an implementation of {@code Remote} to be exported on the RMI registry; note that the {@link NetworkRmi#export(Object, String)}
     * method <b>should</b> be called only by the server, on a remote reference to the client, which was before passed
     * to this {@code remotize} method
     * @param o a reference to the object that you want to export on the registry
     * @param p the local port at which the server will be able to reach the client - leave it {@code 0} to let the OS choose
     * @return a {@code Remote} reference to the exported object: this reference can then be passed to the server and
     *         exported on the registry
     */
    public static Remote remotize(Object o, Integer p) {
        try {
            Remote r = (Remote) o;
            UnicastRemoteObject.exportObject(r, p);
            return r;
        } catch (RemoteException re) {
            Logger.log("Error exporting with UnicastRemoteObject!");
        }   catch (ClassCastException cce) {
            Logger.log("Error casting given object into Remote!");
        }
        return null;
    }

    /**
     * @param o {@code Object}
     * @param n {@code String}
     * @see Connection#export(Object, String)
     */
    @Override
    public void export(Object o, String n) {
        // Format an URL string to be used in RMI registry
        String rmiUrl = "rmi://" + serverAddress + ":" + rmiObjectPort + "/" + n;
        try {
            if (o == null) {
                throw new NullPointerException();
            }
            // Bind the object, after casting it to Remote, to that symbolic URL in the RMI registry
            Naming.rebind(rmiUrl, (Remote) o);
        } catch (RemoteException re) {
            Logger.log("Error binding " + n + " in RMI Registry (@" + this.ip + ")");
        } catch (MalformedURLException mue) {
            Logger.log("Error in URL formatting: " + rmiUrl);
        } catch (NullPointerException npe) {
            Logger.log("Cannot export null object as " + n);
        }
    }

    /**
     * @param name {@code String}
     * @param <T> destination type
     * @return {@code T}
     * @see Connection#getExported(String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getExported(String name) {
        T exportedObject = null;
        try {
            exportedObject = (T) this.rmiRegistry.lookup(name);
        } catch (NotBoundException nbe) {
            Logger.log("Error in lookup for name  " + name + " in RMI Registry: maybe is not bound!");
        } catch (RemoteException re) {
            Logger.log("Error retrieving " + name + " from RMI Registry!");
        } catch (ClassCastException cce) {
            Logger.log("Error casting Remote object into destination class!");
        }
        return exportedObject;
    }

    /**
     *
     * @param callee {@code String}
     * @param methodName {@code String}
     * @param argList {@code Object[]}
     * @return {@code Object}
     * @throws MethodConnectionException when there are connection issues with the server or when the desired object or
     * method are not found on the RMI registry
     * @see Connection#invokeMethod(String, String, Object[])
     */
    @Override
    public Object invokeMethod(String callee, String methodName, Object[] argList) throws MethodConnectionException {
        try {
            Object e = getExported(callee);
            return router.route(e, methodName, argList);
        } catch (Exception e) {
            throw new MethodConnectionException();
        }
    }

    /**
     * Called automatically when an instance is used in a try-with-resources
     * @see Connection#close()
     */
    public void close() {
        if (serverAddress.equals(this.ip) && this.rmiRegistry != null) {
            try {
                UnicastRemoteObject.unexportObject(this.rmiRegistry, true);
            } catch (NoSuchObjectException e) {
                Logger.log("Error shutting registry down");
            }
        }
    }
}