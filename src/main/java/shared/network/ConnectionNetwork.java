package shared.network;

public class ConnectionNetwork {
    private static Connection connection = null;

    private ConnectionNetwork() {
        super();
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection c) {
        if (connection == null) {
            connection = c;
        }
    }
}