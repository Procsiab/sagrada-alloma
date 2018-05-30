package shared.network.rmi;

import shared.Dice;
import shared.Logger;
import shared.Position;
import shared.PositionR;
import shared.TransferObjects.GameManagerT;
import shared.network.Connection;
import shared.network.SharedMiddlewareClient;
import shared.network.SharedMiddlewareServer;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class NetworkRmi implements Connection {
    private static final Integer RMI_METHOD_PORT = 1099;
    private static final String SERVER_ADDRESS = "localhost";

    private Registry rmiRegistry;
    private String ip;
    private Integer rmiObjectPort;

    private void startRegistrySetup(Integer port) {
        try {
            // Set the port to export RMI objects
            this.rmiObjectPort = port;
            // Get local ip
            this.ip = InetAddress.getLocalHost().getHostAddress();
            // Inform the registry about server's address
            System.setProperty("java.rmi.server.hostname", this.ip);
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
        return this.ip;
    }

    @Override
    public Integer getListeningPort() {
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
            if (o == null) {
                throw new NullPointerException();
            }
            // Bind the interface to that symbolic URL in the RMI registry
            Naming.rebind(rmiUrl, remotize(o));
        } catch (RemoteException re) {
            Logger.log("Error binding " + n + " in RMI Registry!");
            Logger.strace(re);
        } catch (MalformedURLException mue) {
            Logger.log("Error in URL formatting: " + rmiUrl);
        } catch (NullPointerException npe) {
            Logger.log("Cannot export null object with name " + n);
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
            Object e = getExported(callee);
            if (e == null) {
                throw new NullPointerException();
            }
            if (e instanceof SharedMiddlewareClient) {
                SharedMiddlewareClient o = (SharedMiddlewareClient) e;
                switch (methodName) {
                    case "deniedAccess":
                        return o.deniedAccess();
                    case "startGame":
                        return o.startGame();
                    case "updateView":
                        o.updateView((GameManagerT) argList[0]);
                        break;
                    case "chooseWindow":
                        return o.chooseWindow((ArrayList<Integer>) argList[0]);
                    case "ping":
                        return o.ping();
                    case "aPrioriWin":
                        o.aPrioriWin();
                        break;
                    case "enable":
                        o.enable();
                        break;
                    case "shut":
                        o.shut();
                        break;
                    case "printScore":
                        o.printScore((Integer) argList[0]);
                        break;
                    case "setWinner":
                        o.setWinner();
                        break;
                    case "chooseWindowBack":
                        return o.chooseWindowBack((Integer) argList[0]);
                    case "startGameViewForced":
                        return o.startGameViewForced();
                    case "placeDice":
                        return o.placeDice((Integer) argList[0], (Position) argList[1]);
                    case "useToolC":
                        return o.useToolC((Integer) argList[0], (Position) argList[1], (Position) argList[2], (Position) argList[3], (Position) argList[4], (PositionR) argList[5], (Integer) argList[6], (Integer) argList[7]);
                    case "exitGame2":
                        o.exitGame2();
                        break;
                    case "endTurn":
                        o.endTurn();
                        break;
                    case "updateViewFromC":
                        o.updateViewFromC();
                        break;
                    default:
                        Logger.log("Requested wrong method " + methodName + " for interface SharedMiddlewareClient!");
                        break;
                }
            } else if (e instanceof SharedMiddlewareServer) {
                SharedMiddlewareServer o = (SharedMiddlewareServer) e;
                switch (methodName) {
                    case "deniedAccess":
                        return o.deniedAccess((String) argList[0]);
                    case "startGame":
                        return o.startGame((String) argList[0], (String) argList[1], (Integer) argList[2], (Boolean) argList[3]);
                    case "updateView":
                        o.updateView((String) argList[0], (GameManagerT) argList[1]);
                        break;
                    case "chooseWindow":
                        return o.chooseWindow((String) argList[0], (ArrayList<Integer>) argList[1]);
                    case "ping":
                        return o.ping((String) argList[0]);
                    case "aPrioriWin":
                        o.aPrioriWin((String) argList[0]);
                        break;
                    case "enable":
                        o.enable((String) argList[0]);
                        break;
                    case "shut":
                        o.shut((String) argList[0]);
                        break;
                    case "printScore":
                        o.printScore((String) argList[0], (Integer) argList[1]);
                        break;
                    case "setWinner":
                        o.setWinner((String) argList[0]);
                        break;
                    case "chooseWindowBack":
                        return o.chooseWindowBack((String) argList[0], (Integer) argList[1]);
                    case "startGameViewForced":
                        return o.startGameViewForced((String) argList[0]);
                    case "placeDice":
                        return o.placeDice((String) argList[0], (Integer) argList[1], (Position) argList[2]);
                    case "useToolC":
                        return o.useToolC((String) argList[0], (Integer) argList[1], (Position) argList[2], (Position) argList[3], (Position) argList[4], (Position) argList[5], (PositionR) argList[6], (Integer) argList[7], (Integer) argList[8]);
                    case "exitGame2":
                        o.exitGame2((String) argList[0]);
                        break;
                    case "endTurn":
                        o.endTurn((String) argList[0]);
                        break;
                    case "updateViewFromC":
                        o.updateViewFromC((String) argList[0]);
                        break;
                    default:
                        Logger.log("Requested wrong method " + methodName + " for interface SharedMiddlewareServer!");
                        break;
                }
            } else {
                Logger.log("Found exported object of wrong type: expected SharedMiddleware<Client|Server>");
            }
        } catch (NullPointerException npe) {
            Logger.log("Could not find requested object " + callee + " among exported ones!");
        } catch (ClassCastException cce) {
            Logger.log("Cast type exception: do your parameters extend Serializable?");
        } catch (RemoteException re) {
            Logger.log("Error calling remote method " + methodName);
            Logger.strace(re);
        }
        return null;
    }

    public void close() {
        //TODO teardown for RMI connection
    }
}