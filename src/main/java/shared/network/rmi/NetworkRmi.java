package shared.network.rmi;

import shared.*;
import shared.network.*;

import java.net.MalformedURLException;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class NetworkRmi implements Connection {
    private static final Integer RMI_METHOD_PORT = 1099;
    private static final Router router = new MethodRouter();

    private static String serverAddress;
    private Registry rmiRegistry;
    private String ip;
    private Integer rmiObjectPort;

    public static void setServerAddress(String address) {
        serverAddress = address;
    }

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

    public NetworkRmi() {
        this(RMI_METHOD_PORT);
    }

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

    @Override
    public void export(Object o, String n) {
        // Format an URL string to be used in RMI registry
        String rmiUrl = "rmi://" + serverAddress + ":" + rmiObjectPort + "/" + n;
        try {
            if (o == null) {
                throw new NullPointerException();
            }
            // Bind the interface to that symbolic URL in the RMI registry
            Naming.rebind(rmiUrl, remotize(o, this.rmiObjectPort));
        } catch (RemoteException re) {
            Logger.log("Error binding " + n + " in RMI Registry (@" + this.ip + ")");
        } catch (MalformedURLException mue) {
            Logger.log("Error in URL formatting: " + rmiUrl);
        } catch (NullPointerException npe) {
            Logger.log("Cannot export null object as " + n);
        }
    }

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

    @Override
    public Object invokeMethod(String callee, String methodName, Object[] argList) throws MethodConnectionException {
        try {
            Object e = getExported(callee);
            return router.route(e, methodName, argList);
        } catch (Exception e) {
            throw new MethodConnectionException();
        }
    }

    public void close() {
        //TODO teardown for RMI connection
    }
}