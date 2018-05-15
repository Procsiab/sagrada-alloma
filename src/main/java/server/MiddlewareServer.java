package server;

import server.threads.GameManager;
import shared.Logger;
import shared.TransferObjects.GameManagerT;
import shared.network.Connection;
import shared.network.SharedMiddlewareClient;
import shared.network.SharedMiddlewareServer;
import shared.network.rmi.NetworkRmi;
import shared.network.socket.NetworkSocket;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;

@SuppressWarnings("ConstantConditions")
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

    private Object callSocketMethod(String uuid, String methodName, Object[] args) {
        try {
            try (Connection client = new NetworkSocket(SReferences.getIpRefEnhanced(uuid), SReferences.getPortRefEnhanced(uuid))) {
                Object ret = client.invokeMethod(uuid, methodName, args);
                return ret;
            } catch (Exception e) {
                Logger.log("An error occurred while invoking method " + methodName + " on host " +
                        SReferences.getIpRefEnhanced(uuid) + "@" + SReferences.getPortRefEnhanced(uuid));
                Logger.strace(e);
            }
        } catch (NullPointerException npe) {
            Logger.log("Unable to find parameter for player with UUID " + uuid);
            Logger.strace(npe);
        }
        return null;
    }

    @Override
    public boolean deniedAccess(String uuid) {
        try {
            GameManager game = SReferences.getGameRefEnhanced(uuid);
            return !game.expected.equals(uuid);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
        }
        return true;
    }

    @Override
    public String startGame(String uuid, String ip, Integer port, Boolean isSocket) {
        return MatchManager.getInstance().startGame(uuid, ip, port, isSocket);
    }

    @Override
    public void updateView(String uuid, GameManagerT gameManager) {
        try {
            if (SReferences.getIsSocketRefEnhanced(uuid)) {
                callSocketMethod(uuid, "updateView", new Object[] {gameManager});
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    client.updateView(gameManager);
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method updateView()");
                    Logger.strace(re);
                }
            }
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
        }
    }

    @Override
    public boolean chooseWindow(String uuid, ArrayList<Integer> windows) {
        try {
            if (SReferences.getIsSocketRefEnhanced(uuid)) {
                return (boolean) callSocketMethod(uuid, "chooseWindow", new Object[] {windows});
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    return client.chooseWindow(windows);
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method chooseWindow()");
                    Logger.strace(re);
                }
            }
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            return false;
        }
        return true;
    }

    @Override
    public boolean ping(String uuid) {
        boolean isReachable = false;
        try {
            if (SReferences.getIsSocketRefEnhanced(uuid)) {
                isReachable = (boolean) callSocketMethod(uuid, "ping", null);
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    isReachable = client.ping();
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method ping()");
                    Logger.strace(re);
                }
            }
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
        }
        return isReachable;
    }

    @Override
    public void aPrioriWin(String uuid) {
        try {
            if (SReferences.getIsSocketRefEnhanced(uuid)) {
                callSocketMethod(uuid, "aPrioriWin", null);
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    client.aPrioriWin();
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method aPrioriWin()");
                    Logger.strace(re);
                }
            }
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
        }
    }

    @Override
    public void enable(String uuid) {
        try {
            if (SReferences.getIsSocketRefEnhanced(uuid)) {
                callSocketMethod(uuid, "enable", null);
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    client.enable();
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method enable()");
                    Logger.strace(re);
                }
            }
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
        }
    }

    @Override
    public void shut(String uuid) {
        try {
            if (SReferences.getIsSocketRefEnhanced(uuid)) {
                callSocketMethod(uuid, "shut", null);
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    client.shut();
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method shut()");
                    Logger.strace(re);
                }
            }
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
        }
    }

    @Override
    public void printScore(String uuid, Integer score) {
        try {
            if (SReferences.getIsSocketRefEnhanced(uuid)) {
                callSocketMethod(uuid, "printScore", new Object[] {score});
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    client.printScore(score);
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method printScore()");
                    Logger.strace(re);
                }
            }
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
        }
    }

    @Override
    public void setWinner(String uuid) {
        try {
            if (SReferences.getIsSocketRefEnhanced(uuid)) {
                callSocketMethod(uuid, "setWinner", null);
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    client.setWinner();
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method setWinner()");
                    Logger.strace(re);
                }
            }
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
        }
    }

    public boolean chooseWindowBack(String uuid, Integer window) {
        try {
            return SReferences.getPlayerRefEnhanced(uuid).setWindow(window);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
        return false;
    }

    public boolean startGameViewForced(String uuid) {
        try {
            if (SReferences.getIsSocketRefEnhanced(uuid)) {
                return (boolean) callSocketMethod(uuid, "startGameViewForced", null);
            } else {
                SharedMiddlewareClient client = serverRmi.getExported(uuid);
                try {
                    return client.startGameViewForced();
                } catch (RemoteException re) {
                    Logger.log("Error calling remote method shut()");
                    Logger.strace(re);
                }
            }
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
        }
        return false;
    }
}