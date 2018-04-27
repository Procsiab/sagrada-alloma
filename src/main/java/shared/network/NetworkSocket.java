package shared.network;

public class NetworkSocket implements Connection {
    public static final Integer SOCKET_PORT = 1101;
    protected static Connection instance = null;
    private String IP;

    /**
     * This method is used to obtain a reference to the singleton class; this will be possible only if {@link NetworkRmiServer#setInstance()}
     * or {@link server.network.NetworkSocketServer#setInstance()} have been called before, otherwise a {@code null} pointer will be returned
     * @return NetworkRmi Returns an instance of an implementation of the {@link NetworkRmi} abstract class, as stated
     * in the class description
     */
    public static Connection getInstance() {
        return instance;
    }
}
