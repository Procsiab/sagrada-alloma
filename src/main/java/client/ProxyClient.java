package client;

import client.gui.LogInScreenController;
import client.gui.StartGameController;
import com.sun.tools.javac.Main;
import shared.Cell;
import shared.Logger;
import shared.Position;
import shared.PositionR;
import shared.TransferObjects.GameManagerT;
import shared.network.MethodConnectionException;
import shared.network.SharedProxyClient;
import shared.network.Connection;
import shared.network.socket.NetworkSocket;

import java.util.ArrayList;

public final class ProxyClient implements SharedProxyClient {
    private static final String SERVER_INTERFACE = "ProxyServer";

    private static String uuid = MainClient.uuid;
    private static Connection connection = null;
    private static Boolean isSocket = false;
    private static ProxyClient instance = new ProxyClient();
    private static LogInScreenController logInScreenController;
    private static StartGameController startGameController;

    private ProxyClient() {
        super();
    }

    public static ProxyClient getInstance() {
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
    public String startGame(String nick) {
        connection.export(instance, uuid);
        Object[] args = {uuid, nick, connection.getIp(), connection.getListeningPort(), isSocket};
        String methodName = "startGame";
        try {
            return (String) connection.invokeMethod(SERVER_INTERFACE, methodName, args);
        } catch (MethodConnectionException mce) {
            return "Connection error";
        }
    }

    @Override
    public void updateView(GameManagerT gameManager) {
        if (MainClient.isPrompt()) {
            MainClient.cliController.updateView(gameManager);
        } else {
            if (MainClient.startGameController != null)
                MainClient.startGameController.updateView(gameManager);
        }
    }

    @Override
    public Boolean chooseWindow(ArrayList<Integer> windows, ArrayList<Cell[][]> matrices) {
        Logger.log("Choose window test");
        if (MainClient.isPrompt()) {
            MainClient.cliController.chooseWindow(windows, matrices);
        } else {
            if (MainClient.waitingRoomController != null)
                MainClient.waitingRoomController.chooseWindow(windows);
        }
        return true;
    }

    public Boolean startGameViewForced() {
        if (MainClient.isPrompt()) {
            MainClient.cliController.startGameViewForced();
        } else {
            if (MainClient.chooseWindowController != null)
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
            //TODO Call GUI method, ensure it is != null with the same pattern as updateviw for example
        }
    }

    @Override
    public void enable() {
        if (MainClient.isPrompt()) {
            MainClient.cliController.enable();
        } else {
            if (MainClient.startGameController != null)
                MainClient.startGameController.enable();
        }
    }

    @Override
    public void shut() {
        if (MainClient.isPrompt()) {
            MainClient.cliController.shut();
        } else {
            if (MainClient.startGameController != null)
                MainClient.startGameController.shut();
        }
    }

    @Override
    public void printScore(Integer score) {
        if (MainClient.isPrompt()) {
            MainClient.cliController.printScore(score);
        } else {
            //TODO Call GUI method...
        }
    }

    @Override
    public void setWinner() {
        if (MainClient.isPrompt()) {
            MainClient.cliController.setWinner();
        } else {
            MainClient.startGameController.setWinner();
        }
    }

    public Boolean chooseWindowBack(Integer window) {
        Object[] args = {uuid, window};
        String methodName = "chooseWindowBack";
        try {
            return (Boolean) connection.invokeMethod(SERVER_INTERFACE, methodName, args);
        } catch (MethodConnectionException mce) {
            return false;
        }
    }

    public Boolean placeDice(Integer index, Position p) {
        Object[] args = {uuid, index, p};
        String methodName = "placeDice";
        try {
            return (Boolean) connection.invokeMethod(SERVER_INTERFACE, methodName, args);
        } catch (MethodConnectionException mce) {
            return false;
        }
    }

    @Override
    public Boolean useToolC(Integer i1, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {
        Object[] args = {uuid, i1, p1, p2, p3, p4, pr, i2, i3};
        String methodName = "useToolC";
        try {
            return (Boolean) connection.invokeMethod(SERVER_INTERFACE, methodName, args);
        } catch (MethodConnectionException mce) {
            return false;
        }
    }

    @Override
    public void exitGame2() {
        Object[] args = {uuid};
        String methodName = "exitGame2";
        try {
            connection.invokeMethod(SERVER_INTERFACE, methodName, args);
        } catch (MethodConnectionException mce) {
            Logger.log("Unable to inform server of the log out");
        } finally {
            connection.close();
        }
    }

    @Override
    public void endTurn() {
        Object[] args = {uuid};
        String methodName = "endTurn";
        try {
            connection.invokeMethod(SERVER_INTERFACE, methodName, args);
        } catch (MethodConnectionException mce) {
            Logger.strace(mce);
        }
    }

    @Override
    public void updateViewFromC() {
        Object[] args = {uuid};
        String methodName = "updateViewFromC";
        try {
            connection.invokeMethod(SERVER_INTERFACE, methodName, args);
        } catch (MethodConnectionException mce) {
            Logger.strace(mce);
        }
    }

    @Override
    public void exitGame1() {
        Object[] args = {uuid};
        String methodName = "exitGame1";
        try {
            connection.invokeMethod(SERVER_INTERFACE, methodName, args);
        } catch (MethodConnectionException mce) {
            Logger.log("Unable to inform server of the log out");
        } finally {
            connection.close();
        }
    }
}