package server.connection;

import server.MatchManager;
import server.SReferences;
import server.threads.GameManager;
import shared.Cell;
import shared.Logger;
import shared.Position;
import shared.PositionR;
import shared.TransferObjects.GameManagerT;
import shared.network.Connection;
import shared.network.MethodConnectionException;
import shared.network.SharedProxyServer;
import shared.network.rmi.NetworkRmi;
import shared.network.socket.NetworkSocket;

import java.util.ArrayList;

public class ProxyServer implements SharedProxyServer {
    private static final String SERVER_INTERFACE = "ProxyServer";

    private static Connection serverSocket = new NetworkSocket();
    private static Connection serverRmi = new NetworkRmi();
    private static ProxyServer instance = new ProxyServer();

    private ProxyServer() {
        super();
        serverRmi.export(this, SERVER_INTERFACE);
        serverSocket.export(this, SERVER_INTERFACE);
    }

    public static ProxyServer getInstance() {
        return instance;
    }

    public static Connection getServerSocket() {
        return serverSocket;
    }

    public static Connection getServerRmi() {
        return serverRmi;
    }

    private Object forwardMethod(String uuid, String methodName, Object[] args) {
        boolean useSocket = false;
        try {
            useSocket = SReferences.getIsSocketRef(uuid);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID: " + uuid);
        }
        if (useSocket) {
            Connection client = null;
            try {
                client = new NetworkSocket(SReferences.getIpRef(uuid), SReferences.getPortRef(uuid));
                return client.invokeMethod(uuid, methodName, args);
            } catch (MethodConnectionException mce) {
                Logger.log(SReferences.getGameRef(uuid) + ", player " + uuid + " socket error occurred while invoking method " + methodName);
            } finally {
                if (client != null) {
                    client.close();
                }
            }
        } else {
            try {
                return serverRmi.invokeMethod(uuid, methodName, args);
            } catch (MethodConnectionException mce) {
                Logger.log(SReferences.getGameRef(uuid) + ", player " + uuid + " RMI error occurred while invoking method " + methodName);
            }
        }
        return null;
    }

    private Boolean deniedAccess(String uuid) {
        try {
            GameManager game = SReferences.getGameRef(uuid);
            String expected = game.getExpected();

            if (expected.equals("all"))
                return false;
            else if (expected.equals("none") || !expected.equals(uuid)) {
                Logger.log("Denied access for UUID " + uuid);
                return true;
            }
            return false;
        } catch (NullPointerException npe) {
            return true;
        }
    }

    @Override
    public String startGame(String uuid, String nick, String ip, Integer port, Boolean isSocket) {
        return MatchManager.startGame(uuid, nick, ip, port, isSocket);
    }

    @Override
    public void updateView(String uuid, GameManagerT gameManager) {
        forwardMethod(uuid, "updateView", new Object[]{gameManager});
    }

    @Override
    public Boolean chooseWindow(String uuid, ArrayList<Integer> windows, ArrayList<Cell[][]> matrices) {
        Boolean ret = (Boolean) forwardMethod(uuid, "chooseWindow", new Object[]{windows, matrices});
        if (ret != null) {
            return ret;
        } else {
            return false;
        }
    }

    @Override
    public Boolean ping(String uuid) {
        Boolean ret = (Boolean) forwardMethod(uuid, "ping", null);
        if (ret != null) {
            return ret;
        } else {
            return false;
        }
    }

    @Override
    public void tavoloWin(String uuid) {
        forwardMethod(uuid, "tavoloWin", null);
    }

    @Override
    public void enable(String uuid) {
        forwardMethod(uuid, "enable", null);
    }

    @Override
    public void shut(String uuid) {
        forwardMethod(uuid, "shut", null);
    }

    @Override
    public void printScore(String uuid, ArrayList<String> nicks, ArrayList<Integer> scores, ArrayList<Boolean> winner) {
        forwardMethod(uuid, "printScore", new Object[]{nicks, scores});
    }

    @Override
    public Boolean chooseWindowBack(String uuid, Integer window) {
        try {
            if (deniedAccess(uuid))
                return false;
            return SReferences.getPlayerRef(uuid).setWindowFromC(window);
        } catch (NullPointerException npe) {
            return false;
        }
    }

    @Override
    public Boolean startGameViewForced(String uuid) {
        Boolean ret = (Boolean) forwardMethod(uuid, "startGameViewForced", null);
        if (ret != null) {
            return ret;
        } else {
            return false;
        }
    }

    @Override
    public Boolean placeDice(String uuid, Integer index, Position p) {
        try {
            if (deniedAccess(uuid))
                return false;
            Boolean value;
            GameManager game = SReferences.getGameRef(uuid);
            game.getThreads().incrementAndGet();
            value = SReferences.getPlayerRef(uuid).placeDice(index, p);
            game.getThreads().decrementAndGet();
            return value;
        } catch (NullPointerException npe) {
            return false;
        }
    }

    @Override
    public Boolean useToolC(String uuid, Integer i1, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {
        try {
            if (deniedAccess(uuid))
                return false;
            Boolean value;
            GameManager game = SReferences.getGameRef(uuid);
            game.getThreads().incrementAndGet();
            value = SReferences.getPlayerRef(uuid).useTool(uuid, i1, p1, p2, p3, p4, pr, i2, i3);
            game.getThreads().decrementAndGet();
            return value;
        } catch (NullPointerException npe) {
            return false;
        }
    }

    @Override
    public void exitGame2(String uuid) {
        try {
            SReferences.getGameRef(uuid).exitGame2(uuid);
        } catch (NullPointerException npe) {
            Logger.log("Unable to log out player with UUID " + uuid);
        }
    }

    @Override
    public void endTurn(String uuid) {
        try {
            if (deniedAccess(uuid))
                return;
            GameManager game = SReferences.getGameRef(uuid);
            game.getThreads().incrementAndGet();
            SReferences.getGameRef(uuid).endTurn();
            game.getThreads().decrementAndGet();
        } catch (NullPointerException npe) {
            Logger.log("Unable to terminate turn for player with UUID " + uuid);
        }
    }

    @Override
    public void updateViewFromC(String uuid) {
        try {
            if (deniedAccess(uuid))
                return;
            GameManager game = SReferences.getGameRef(uuid);
            game.getThreads().incrementAndGet();
            SReferences.getPlayerRef(uuid).updateViewFromC(uuid);
            game.getThreads().decrementAndGet();
        } catch (NullPointerException npe) {
            Logger.log("Unable to update teh view of player with UUID " + uuid);
        }
    }

    @Override
    public Boolean exitGame1(String uuid) {
        try {
            return MatchManager.exitGame1(uuid);
        } catch (NullPointerException npe) {
            Logger.log("Unable to log out player with UUID " + uuid);
        }
        return false;
    }

}