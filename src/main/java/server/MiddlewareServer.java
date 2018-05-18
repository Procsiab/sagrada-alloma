package server;

import server.threads.GameManager;
import shared.Logger;
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
        forwardMethod(uuid, "updateView", new Object[] {gameManager});
    }

    @Override
    public boolean chooseWindow(String uuid, ArrayList<Integer> windows) {
        return (boolean) forwardMethod(uuid, "chooseWindow", new Object[] {windows});
    }

    @Override
    public boolean ping(String uuid) {
        return (boolean) forwardMethod(uuid, "ping", null);
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
        forwardMethod(uuid, "printScore", new Object[] {score});
    }

    @Override
    public void setWinner(String uuid) {
        forwardMethod(uuid, "setWinner", null);
    }

    public boolean chooseWindowBack(String uuid, Integer window) {
        try {
            return SReferences.getPlayerRefEnhanced(uuid).setWindow(window-1);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
        return false;
    }

    public boolean startGameViewForced(String uuid) {
        return (boolean) forwardMethod(uuid, "startGameViewForced", null);
    }
}