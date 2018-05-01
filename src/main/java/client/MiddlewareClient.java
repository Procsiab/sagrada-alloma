package client;

import shared.network.SharedMiddleware;
import shared.network.Connection;
import shared.network.socket.NetworkSocket;

public final class MiddlewareClient {
    private static final String SERVER_INTERFACE = "MiddlewareServer";
    private static Connection connection = null;
    private static boolean isSocket = false;
    private static MiddlewareClient instance = new MiddlewareClient();

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

    public String startGame(String uuid) {
        if (isSocket) {
            Object[] args = {uuid};
            String methodName = this.getClass().getEnclosingMethod().getName();
            return (String) connection.invokeMethod(SERVER_INTERFACE, methodName, args);
        } else {
            SharedMiddleware server = connection.getExported(SERVER_INTERFACE);
            return server.startGame(uuid, false);
        }
    }
}