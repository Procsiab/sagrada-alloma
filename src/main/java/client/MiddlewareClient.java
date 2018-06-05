package client;

import client.gui.LogInScreenController;
import client.gui.StartGameController;
import shared.Cell;
import shared.Logger;
import shared.Position;
import shared.PositionR;
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
    public Boolean deniedAccess() {
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
        if (MainClient.isPrompt()) {
            MainClient.cliController.updateView(gameManager);
        } else {
            MainClient.startGameController.updateView(gameManager);
        }
    }

    @Override
    public Boolean chooseWindow(ArrayList<Integer> windows, ArrayList<Cell[][]> matrices) {
        Logger.log("Choose window test");
        if (MainClient.isPrompt()) {
            MainClient.cliController.chooseWindow(windows, matrices);
        } else {
            MainClient.waitingRoomController.chooseWindow(windows);
        }
        return true;
    }

    public Boolean startGameViewForced() {
        if (MainClient.isPrompt()) {
            MainClient.cliController.startGameViewForced();
        } else {
            if (MainClient.startGameController == null)
                MainClient.chooseWindowController.startGameViewForced();
        }
        Logger.log("OK client start forced");
        return true;
    }

    @Override
    public Boolean ping() {
        return true;
    }

    @Override
    public void aPrioriWin() {
        if (MainClient.isPrompt()) {
            MainClient.cliController.aPrioriWin();
        } else {
            //TODO Call GUI method
        }
    }

    @Override
    public void enable() {
        if (MainClient.isPrompt()) {
            MainClient.cliController.enable();
        } else {
            MainClient.startGameController.enable();         }
    }

    @Override
    public void shut() {
        if (MainClient.isPrompt()) {
            MainClient.cliController.shut();
        } else {
            MainClient.startGameController.shut();        }
    }

    @Override
    public void printScore(Integer score) {
        if (MainClient.isPrompt()) {
            MainClient.cliController.printScore(score);
        } else {
            //TODO Call GUI method
        }
    }

    @Override
    public void setWinner() {
        if (MainClient.isPrompt()) {
            MainClient.cliController.setWinner();
        } else {
            //TODO Call GUI method
        }
    }

    public Boolean chooseWindowBack(Integer window) {
        Object[] args = {uuid, window};
        String methodName = "chooseWindowBack";
        Boolean ret = (Boolean) connection.invokeMethod(SERVER_INTERFACE, methodName, args);
        if (ret != null) {
            return ret;
        } else {
            return false;
        }
    }

    public Boolean placeDice(Integer index, Position p) {
        Object[] args = {uuid, index, p};
        String methodName = "placeDice";
        Boolean ret = (Boolean) connection.invokeMethod(SERVER_INTERFACE, methodName, args);
        if (ret != null) {
            return ret;
        } else {
            return false;
        }
    }

    @Override
    public Boolean useToolC(Integer i1, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {
        Object[] args = {uuid, i1, p1, p2, p3, p4, pr, i2, i3};
        String methodName = "useToolC";
        Boolean ret = (Boolean) connection.invokeMethod(SERVER_INTERFACE, methodName, args);
        if (ret != null) {
            return ret;
        } else {
            return false;
        }
    }

    @Override
    public void exitGame2() {
        Object[] args = {uuid};
        String methodName = "exitGame2";
        connection.invokeMethod(SERVER_INTERFACE, methodName, args);
    }

    @Override
    public void endTurn() {
        Object[] args = {uuid};
        String methodName = "endTurn";
        connection.invokeMethod(SERVER_INTERFACE, methodName, args);
    }

    @Override
    public void updateViewFromC() {
        Object[] args = {uuid};
        String methodName = "updateViewFromC";
        connection.invokeMethod(SERVER_INTERFACE, methodName, args);
    }
}