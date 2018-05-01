package server;

import shared.Logger;
import shared.SharedServerGameManager;
import shared.network.Connection;
import shared.network.SharedMiddlewareClient;
import shared.network.SharedMiddlewareServer;
import shared.network.rmi.NetworkRmi;
import shared.network.socket.NetworkSocket;

import java.rmi.RemoteException;

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

    @Override
    public String startGame(String uuid, String ip, Integer port, Boolean isSocket) {
        return MatchManager.getInstance().startGame("UUID", ip, port, isSocket);
    }

    @Override
    public void updateView(String uuid, SharedServerGameManager gameManager) {
        int playerId = SReferences.uuidRef.indexOf(uuid);
        if (playerId >= 0) {
            if (SReferences.isSocketRef.get(playerId)) {
                Object[] args = {gameManager};
                String methodName = "updateView";
                try (Connection client = new NetworkSocket(SReferences.ipRef.get(playerId), SReferences.portRef.get(playerId))) {
                    client.invokeMethod(uuid, methodName, args);
                } catch (Exception e) {
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
            Logger.log("Unable to find player with UUID " + uuid + " in SReferences!");
        }
    }
}
