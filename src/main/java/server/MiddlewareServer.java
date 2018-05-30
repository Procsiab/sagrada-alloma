package server;

import server.threads.GameManager;
import shared.Logger;
import shared.Position;
import shared.PositionR;
import shared.TransferObjects.GameManagerT;
import shared.network.Connection;
import shared.network.SharedMiddlewareServer;
import shared.network.rmi.NetworkRmi;
import shared.network.socket.NetworkSocket;

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

    private Object forwardMethod(String uuid, String methodName, Object[] args) {
        try {
            if (SReferences.getIsSocketRefEnhanced(uuid)) {
                try (Connection client = new NetworkSocket(SReferences.getIpRefEnhanced(uuid), SReferences.getPortRefEnhanced(uuid))) {
                    return client.invokeMethod(uuid, methodName, args);
                } catch (Exception e) {
                    Logger.log("An error occurred while invoking method " + methodName + " on host " +
                            SReferences.getIpRefEnhanced(uuid) + "@" + SReferences.getPortRefEnhanced(uuid));
                    Logger.strace(e);
                }
            } else {
                return serverRmi.invokeMethod(uuid, methodName, args);
            }
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
        return null;
    }

    @Override
    public Boolean deniedAccess(String uUID) {
        try {
            GameManager game = SReferences.getGameRefEnhanced(uUID);
            String expected = game.getExpected();
            if (expected.equals("all"))
                return false;
            else if (expected.equals("none"))
                return true;
            return !expected.equals(uUID);
        }  catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uUID);
        }
        return true;
    }

    @Override
    public String startGame(String uuid, String ip, Integer port, Boolean isSocket) {
        return MatchManager.getInstance().startGame(uuid, ip, port, isSocket);
    }

    @Override
    public void updateView(String uuid, GameManagerT gameManager) {
        forwardMethod(uuid, "updateView", new Object[]{gameManager});
    }

    @Override
    public Boolean chooseWindow(String uuid, ArrayList<Integer> windows) {
        Boolean ret = (Boolean) forwardMethod(uuid, "chooseWindow", new Object[]{windows});
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
    public void aPrioriWin(String uuid) {
        forwardMethod(uuid, "aPrioriWin", null);
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
    public void printScore(String uuid, Integer score) {
        forwardMethod(uuid, "printScore", new Object[]{score});
    }

    @Override
    public void setWinner(String uuid) {
        forwardMethod(uuid, "setWinner", null);
    }

    public Boolean chooseWindowBack(String uuid, Integer window) {
        try {
            if (deniedAccess(uuid))
                return false;
            return SReferences.getPlayerRefEnhanced(uuid).setWindowFromC(window - 1);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
        return false;
    }

    public Boolean startGameViewForced(String uuid) {
        Boolean ret = (Boolean) forwardMethod(uuid, "startGameViewForced", null);
        if (ret != null) {
            return ret;
        } else {
            return false;
        }
    }

    public Boolean placeDice(String uuid, Integer index, Position p) {
        try {
            if (deniedAccess(uuid))
                return false;
            return SReferences.getPlayerRefEnhanced(uuid).placeDice(index, p);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
        return false;
    }

    @Override
    public Boolean useToolC(String uuid, Integer i1, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {
        try {
            if (deniedAccess(uuid))
                return false;
            return SReferences.getPlayerRefEnhanced(uuid).useToolC(i1, p1, p2, p3, p4, pr, i2, i3);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
        return false;
    }

    @Override
    public void exitGame2(String uuid) {
        try {
            SReferences.getGameRefEnhanced(uuid).exitGame2(uuid);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
    }

    @Override
    public boolean endTurn(String uuid) {
        try {
            if (deniedAccess(uuid))
                return false;
            SReferences.getGameRefEnhanced(uuid).endTurn(uuid);
            return true;
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
            return false;
        }
    }

    @Override
    public void updateViewFromC(String uuid) {
        try {
            if (deniedAccess(uuid))
                return;
            SReferences.getGameRefEnhanced(uuid).updateView(uuid);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
    }
}