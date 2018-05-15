package client;

import client.gui.LogInScreenController;
import client.gui.StartGameController;
import shared.TransferObjects.GameManagerT;
import shared.network.SharedMiddlewareClient;
import shared.network.Connection;
import shared.network.socket.NetworkSocket;
import java.util.ArrayList;

public final class MiddlewareClient implements SharedMiddlewareClient {
    private static final String SERVER_INTERFACE = "MiddlewareServer";

    private static String uuid = MainClient.uuid;
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
    public boolean deniedAccess() {
        Object[] args = {uuid};
        String methodName = "deniedAccess";
        return (boolean) connection.invokeMethod(SERVER_INTERFACE, methodName, args);
    }

    @Override
    public String startGame() {
        connection.export(instance, uuid);
        Object[] args = {uuid, connection.getIp(), connection.getListeningPort(), isSocket};
        String methodName = "startGame";
        return (String) connection.invokeMethod(SERVER_INTERFACE, methodName, args);
    }

    @Override
    public void updateView(GameManagerT gameManager) {
        MainClient.startGameController.updateView(gameManager);
    }

    @Override
    public boolean chooseWindow(ArrayList<Integer> windows) {
        System.out.println("CHOOSE WINDOW TEST");
        MainClient.waitingRoomController.chooseWindow(windows);
        return true;
        //TODO Call true method
    }

    public boolean startGameViewForced(){
        MainClient.chooseWindowController.startGameViewForced();
        return true;
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

    public boolean chooseWindowBack(Integer window) {
        Object[] args = {uuid, window};
        String methodName = "chooseWindowBack";
        return (boolean) connection.invokeMethod(SERVER_INTERFACE, methodName, args);
    }
}