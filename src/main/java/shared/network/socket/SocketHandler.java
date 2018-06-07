package shared.network.socket;

import shared.*;
import shared.TransferObjects.GameManagerT;
import shared.network.SharedMiddlewareClient;
import shared.network.SharedMiddlewareServer;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

class SocketHandler implements Runnable, Closeable {
    private Socket client;
    private Map<String, Object> exportedObjects;

    SocketHandler(Socket client, Map<String, Object> objects) {
        this.client = client;
        this.exportedObjects = objects;
    }

    @Override
    public void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            in = new ObjectInputStream(client.getInputStream());
            out = new ObjectOutputStream(client.getOutputStream());
            Object data = in.readObject();
            Object resp;
            Logger.log("Reading client data from socket...");
            //TODO Use smarter packet handling
            if (data instanceof ObjectRequestPacket) {
                ObjectRequestPacket req = (ObjectRequestPacket) data;
                resp = getExported(req.objectName);
            } else if (data instanceof MethodRequestPacket) {
                MethodRequestPacket req = (MethodRequestPacket) data;
                resp = invokeMethod(req.callee, req.methodName, req.arguments);
            } else {
                throw new ClassNotFoundException();
            }
            out.writeObject(resp);
            out.flush();
        } catch (IOException ioe) {
            Logger.log("Error in handling client input!");
        } catch (ClassNotFoundException cnfe) {
            Logger.log("Received data has unknown class!");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                Logger.log("Error closing ObjectInputStream after handling client");
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ioe) {
                Logger.log("Error closing ObjectOutputStream after handling client");
            }
            this.close();
        }
    }

    @SuppressWarnings("unchecked")
    private  <T> T getExported(String name) {
        T exportedObject = null;
        try {
            exportedObject = (T) exportedObjects.get(name);
        } catch (ClassCastException cce) {
            Logger.log("Error casting exported object " + name + " into destination class!");
        }
        return exportedObject;
    }

    private Object invokeMethod(String callee, String methodName, Object[] argList) {
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
                        return o.chooseWindow((ArrayList<Integer>) argList[0], (ArrayList<Cell[][]>) argList[1]);
                    case "ping":
                        return o.ping();
                    case "tavoloWin":
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
                        return o.chooseWindow((String) argList[0], (ArrayList<Integer>) argList[1], (ArrayList<Cell[][]>) argList[2]);
                    case "ping":
                        return o.ping((String) argList[0]);
                    case "tavoloWin":
                        o.tavoloWin((String) argList[0]);
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
            Logger.log("Null pointer: maybe object " + callee + " wasn't exported!");
            Logger.strace(npe, true);
        } catch (ClassCastException cce) {
            Logger.log("Cast type exception: do your parameters extend Serializable?");
        } catch (RemoteException re) {
            Logger.log("Error calling remote method " + methodName);
        }
        return null;
    }

    @Override
    public void close() {
        try {
            client.close();
        } catch (IOException ioe) {
            Logger.log("Error closing socket in SocketHandler");
        }
    }
}