package client;

import client.gui.LogInScreenController;
import client.gui.StartGameController;
import shared.GameManager;
import shared.Logger;
import shared.network.SharedMiddlewareClient;
import shared.network.SharedMiddlewareServer;
import shared.network.Connection;
import shared.network.socket.NetworkSocket;

import java.rmi.RemoteException;
import java.util.ArrayList;

public final class MiddlewareClient implements SharedMiddlewareClient {
    private static final String SERVER_INTERFACE = "MiddlewareServer";

    private static Connection connection = null;
    private static Boolean isSocket = false;
    private static MiddlewareClient instance = new MiddlewareClient();
    private static LogInScreenController logInScreenController;
    private static StartGameController startGameController;

    private MiddlewareClient() {
        super();
    }

    public static MiddlewareClient getInstance() {
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection c) {
        if (connection == null) {
            connection = c;
            isSocket = c instanceof NetworkSocket;
        }
    }

    @Override
    public String startGame(String uuid) {
        connection.export(instance, uuid);
        if (isSocket) {
            Object[] args = {uuid, connection.getIp(), connection.getListeningPort(), isSocket};
            String methodName = "startGame";
            return (String) connection.invokeMethod(SERVER_INTERFACE, methodName, args);
        } else {
            SharedMiddlewareServer server = connection.getExported(SERVER_INTERFACE);
            try {
                return server.startGame(uuid, connection.getIp(), connection.getListeningPort(), isSocket);
            } catch (RemoteException re) {
                Logger.log("Error calling remote method startGame()");
                Logger.strace(re);
                return "Cannot contact server!";
            }
        }
    }

    @Override
    public void updateView(GameManager gameManager) {
        MainClient.startGameController.updateView(gameManager);
    }

    @Override
    public Integer chooseWindow(ArrayList<Integer> windows) {
        System.out.println("I'm blue");
        return 2;
        //TODO Call true method
    }

    @Override
    public boolean ping() {
        return true;
    }

    @Override
    public void aPrioriWin() {
        //TODO Call true method
    }

    @Override
    public void enable() {
        //TODO Call true method
    }

    @Override
    public void shut() {
        //TODO Call true method
    }

    @Override
    public void printScore(Integer score) {
        //TODO Call true method
    }

    @Override
    public void setWinner() {
        //TODO Call true method
    }
}