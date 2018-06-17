package client;

import shared.Cell;
import shared.Logger;
import shared.Position;
import shared.PositionR;
import shared.TransferObjects.GameManagerT;
import shared.network.MethodConnectionException;
import shared.network.SharedProxyClient;
import shared.network.Connection;
import shared.network.rmi.NetworkRmi;
import shared.network.socket.NetworkSocket;

import java.util.ArrayList;

public final class ProxyClient implements SharedProxyClient {
    private static final String SERVER_INTERFACE = "ProxyServer";

    private static String uuid = MainClient.uuid;
    private static Connection connection = null;
    private static Boolean isSocket = false;
    private static ProxyClient instance = new ProxyClient();

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
        Object stub = instance;
        Integer port = -1;
        if (isSocket) {
            // First register the stub for remote calls, starting the consumer
            connection.export(stub, uuid);
            // Get consumer's port after starting the consumer
            port = connection.getListeningPort();
            // Set the stub to null, as without calling exportObject won't be passed as reference
            stub = null;
        } else {
            // This static method calls UnicastRemoteObject's exportObject
            NetworkRmi.remotize(stub, 0);
        }
        Object[] args = {uuid, nick, connection.getLocalIp(), port, isSocket, stub};
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
    public void printScore(ArrayList<String> nicks, ArrayList<Integer> scores, ArrayList<Boolean> winner) {
        if (MainClient.isPrompt()) {
            MainClient.cliController.printScore(nicks, scores, winner);
        } else {
            MainClient.startGameController.printScore(nicks, scores, winner);
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