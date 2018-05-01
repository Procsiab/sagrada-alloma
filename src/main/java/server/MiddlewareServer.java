package server;

import shared.network.Connection;
import shared.network.SharedMiddleware;
import shared.network.rmi.NetworkRmi;
import shared.network.socket.NetworkSocket;

public class MiddlewareServer implements SharedMiddleware {
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

    public String startGame(String uuid, boolean isSocket) {
        return MatchManager.getInstance().startGame("UUID", isSocket);
    }
}
