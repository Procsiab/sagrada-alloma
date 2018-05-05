package shared.network.rmi;

import shared.Logger;
import shared.network.Connection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import java.util.stream.Collectors;

public class NetworkRmi implements Connection {
    private static final Integer RMI_METHOD_PORT = 1099;
    private static final String SERVER_ADDRESS = "localhost";

    private Registry rmiRegistry;
    private String IP;
    private Integer rmiObjectPort;

    private void startRegistrySetup(Integer port) {
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

    public NetworkRmi(Integer port) {
        startRegistrySetup(port);
        try {
            // Start RMI registry on this machine
            this.rmiRegistry = LocateRegistry.createRegistry(port);
        } catch (RemoteException re) {
            Logger.log("Error in RMI Registry initialization!");
            Logger.strace(re);
        }
    }

    public NetworkRmi(String server, Integer port) {
        startRegistrySetup(port);
        try {
            // Obtain RMI registry reference from server
            if (server.equals("")) {
                rmiRegistry = LocateRegistry.getRegistry(SERVER_ADDRESS, port);
            } else {
                rmiRegistry = LocateRegistry.getRegistry(server, port);
            }
        } catch (RemoteException re) {
            Logger.log("Error in RMI Registry connection! (server: " + server + ")");
            Logger.strace(re);
        }
    }

    public NetworkRmi() {
        this(RMI_METHOD_PORT);
    }

    public NetworkRmi(String server) {
        this(server, RMI_METHOD_PORT);
    }

    @Override
    public String getIp() {
        return this.IP;
    }

    @Override
    public Integer getLocalPort() {
        return -1;
    }

    private Remote remotize(Object o) {
        try {
            Remote r = (Remote) o;
            UnicastRemoteObject.exportObject(r, this.rmiObjectPort);
            return r;
        } catch (RemoteException re) {
            Logger.log("Error exporting with UnicastRemoteObject!");
            Logger.strace(re);
        }   catch (ClassCastException cce) {
            Logger.log("Error casting given object into Remote!");
        }
        return null;
    }

    @Override
    public void export(Object o, String n) {
        // Format an URL string to be used in RMI registry
        String rmiUrl = "//" + this.getIp() + ":" + RMI_METHOD_PORT.toString() + "/" + n;
        try {
            // Bind the interface to that symbolic URL in the RMI registry
            Naming.rebind(rmiUrl, remotize(o));
        } catch (RemoteException re) {
            Logger.log("Error binding " + n + " in RMI Registry!");
            Logger.strace(re);
        } catch (MalformedURLException mue) {
            Logger.log("Error in URL formatting: " + rmiUrl);
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
            Logger.strace(re);
        } catch (ClassCastException cce) {
            Logger.log("Error casting Remote object into destination class!");
        }
        return exportedObject;
    }

    @Override
    public Object invokeMethod(String callee, String methodName, Object[] argList) {
        try {
            Class[] parameters = {};
            Object o = getExported(callee);
            Method m;
            if (o != null) {
                if (argList == null) {
                    m = o.getClass().getDeclaredMethod(methodName, parameters);
                } else {
                    Arrays.stream(argList).map(Object::getClass).collect(Collectors.toList()).toArray(parameters);
                    m = o.getClass().getDeclaredMethod(methodName, parameters);
                }
                m.setAccessible(true);
                return m.invoke(getExported(callee), argList);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException npe) {
            Logger.log("Could not find requested object " + callee + " among exported ones!");
        } catch (ClassCastException cce) {
            Logger.log("The given object should extend Serializable!");
        } catch (NoSuchMethodException nsme) {
            Logger.log("Requested method " + methodName + " was not found in " + callee + " class!");
        } catch (InvocationTargetException ite) {
            Logger.log("An exception occurred in method " + methodName + "!");
            Logger.strace(ite);
        } catch (IllegalAccessException iae) {
            Logger.log("Error accessing method " + methodName + " on object " + callee + "!");
            Logger.strace(iae);
        }
        return null;
    }

    public void close() {

    }
}