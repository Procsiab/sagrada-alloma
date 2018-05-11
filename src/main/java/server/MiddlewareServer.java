package server;

import shared.GameManager;
import shared.Logger;
import shared.network.Connection;
import shared.network.SharedMiddlewareClient;
import shared.network.SharedMiddlewareServer;
import shared.network.rmi.NetworkRmi;
import shared.network.socket.NetworkSocket;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class MiddlewareServer implements SharedMiddlewareServer {
    private static final String SERVER_INTERFACE = "MiddlewareServer";

    private static Connection serverSocket = new NetworkSocket();
    private static Connection serverRmi = new NetworkRmi();
    private static MiddlewareServer instance = new MiddlewareServer();

    private MiddlewareServer() {
        super();
        serverRmi.export(this, SERVER_INTERFACE);
        serverSocket.export(this, SERVER_INTERFACE);
    }

    public static MiddlewareServer getInstance() {
        return instance;
    }

    public static Connection getServerSocket() {
        return serverSocket;
    }

    public static Connection getServerRmi() {
        return serverRmi;
    }

    //TODO Look this method
    public static boolean deniedAccess(String uUID){
        //could be useful have a method to see if client is allowed to speak to server
        GameManager game = SReferences.getGameRef().get(SReferences.getUuidRef().indexOf(uUID));
        if(game.expected.equals(uUID))
            return false;
        return true;
    }

    @Override
    public String startGame(String uuid, String ip, Integer port, Boolean isSocket) {
        return MatchManager.getInstance().startGame(uuid, ip, port, isSocket);
    }

    @Override
    public void updateView(String uuid, GameManager gameManager) {
        int playerId = SReferences.getUuidRef().indexOf(uuid);
        if (playerId >= 0) {
            if (SReferences.getIsSocketRef().get(playerId)) {
                Object[] args = {gameManager};
                String methodName = "updateView";
                try (Connection client = new NetworkSocket(SReferences.getIpRef().get(playerId), SReferences.getPortRef().get(playerId))) {
                    client.invokeMethod(uuid, methodName, args);
                } catch (Exception e) {
                    Logger.log("An error occurred while invoking method " + methodName +  " on host " +
                            SReferences.getIpRef().get(playerId) + "@" + SReferences.getPortRef().get(playerId));
                    Logger.strace(e);
                }
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    client.updateView(gameManager);
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method updateView()");
                    Logger.strace(re);
                }
            }
        } else {
            Logger.log("Unable to find player with UUID " + uuid);
        }
    }

    @Override
    public Integer chooseWindow(String uuid, ArrayList<Integer> windows) {
        int playerId = SReferences.getUuidRef().indexOf(uuid);
        if (playerId >= 0) {
            if (SReferences.getIsSocketRef().get(playerId)) {
                Object[] args = {windows};
                String methodName = "chooseWindow";
                try (Connection client = new NetworkSocket(SReferences.getIpRef().get(playerId), SReferences.getPortRef().get(playerId))) {
                    return (Integer) client.invokeMethod(uuid, methodName, args);
                } catch (Exception e) {
                    Logger.strace(e);
                }
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    return client.chooseWindow(windows);
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method chooseWindow()");
                    Logger.strace(re);
                }
            }
        } else {
            Logger.log("Unable to find player with UUID " + uuid);
        }
        return -1;
    }

    @Override
    public boolean ping(String uuid) {
        int playerId = SReferences.getUuidRef().indexOf(uuid);
        if (playerId >= 0) {
            if (SReferences.getIsSocketRef().get(playerId)) {
                String methodName = "ping";
                try (Connection client = new NetworkSocket(SReferences.getIpRef().get(playerId), SReferences.getPortRef().get(playerId))) {
                    return (boolean) client.invokeMethod(uuid, methodName, null);
                } catch (Exception e) {
                    Logger.strace(e);
                }
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    return client.ping();
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method ping()");
                    Logger.strace(re);
                }
            }
        } else {
            Logger.log("Unable to find player with UUID " + uuid);
        }
        return false;
    }

    @Override
    public void aPrioriWin(String uuid) {
        int playerId = SReferences.getUuidRef().indexOf(uuid);
        if (playerId >= 0) {
            if (SReferences.getIsSocketRef().get(playerId)) {
                String methodName = "aPrioriWin";
                try (Connection client = new NetworkSocket(SReferences.getIpRef().get(playerId), SReferences.getPortRef().get(playerId))) {
                    client.invokeMethod(uuid, methodName, null);
                } catch (Exception e) {
                    Logger.strace(e);
                }
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    client.aPrioriWin();
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method aPrioriWin()");
                    Logger.strace(re);
                }
            }
        } else {
            Logger.log("Unable to find player with UUID " + uuid);
        }
    }

    @Override
    public void enable(String uuid) {
        int playerId = SReferences.getUuidRef().indexOf(uuid);
        if (playerId >= 0) {
            if (SReferences.getIsSocketRef().get(playerId)) {
                String methodName = "enable";
                try (Connection client = new NetworkSocket(SReferences.getIpRef().get(playerId), SReferences.getPortRef().get(playerId))) {
                    client.invokeMethod(uuid, methodName, null);
                } catch (Exception e) {
                    Logger.strace(e);
                }
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    client.enable();
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method enable()");
                    Logger.strace(re);
                }
            }
        } else {
            Logger.log("Unable to find player with UUID " + uuid);
        }
    }

    @Override
    public void shut(String uuid) {
        int playerId = SReferences.getUuidRef().indexOf(uuid);
        if (playerId >= 0) {
            if (SReferences.getIsSocketRef().get(playerId)) {
                String methodName = "shut";
                try (Connection client = new NetworkSocket(SReferences.getIpRef().get(playerId), SReferences.getPortRef().get(playerId))) {
                    client.invokeMethod(uuid, methodName, null);
                } catch (Exception e) {
                    Logger.strace(e);
                }
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    client.shut();
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method shut()");
                    Logger.strace(re);
                }
            }
        } else {
            Logger.log("Unable to find player with UUID " + uuid);
        }
    }

    @Override
    public void printScore(String uuid, Integer score) {
        int playerId = SReferences.getUuidRef().indexOf(uuid);
        if (playerId >= 0) {
            if (SReferences.getIsSocketRef().get(playerId)) {
                Object[] args = {score};
                String methodName = "printScore";
                try (Connection client = new NetworkSocket(SReferences.getIpRef().get(playerId), SReferences.getPortRef().get(playerId))) {
                    client.invokeMethod(uuid, methodName, args);
                } catch (Exception e) {
                    Logger.strace(e);
                }
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    client.printScore(score);
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method printScore()");
                    Logger.strace(re);
                }
            }
        } else {
            Logger.log("Unable to find player with UUID " + uuid);
        }
    }

    @Override
    public void setWinner(String uuid) {
        int playerId = SReferences.getUuidRef().indexOf(uuid);
        if (playerId >= 0) {
            if (SReferences.getIsSocketRef().get(playerId)) {
                String methodName = "setWinner";
                try (Connection client = new NetworkSocket(SReferences.getIpRef().get(playerId), SReferences.getPortRef().get(playerId))) {
                    client.invokeMethod(uuid, methodName, null);
                } catch (Exception e) {
                    Logger.strace(e);
                }
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    client.setWinner();
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method setWinner()");
                    Logger.strace(re);
                }
            }
        } else {
            Logger.log("Unable to find player with UUID " + uuid);
        }
    }
}
