package shared.network;

import shared.Logger;

import java.net.InetAddress;
import java.net.Socket;

/**
 * <h1>Network Socket Abstract Class</h1>
 */
public abstract class NetworkSocket implements Connection {
    public static final Integer SOCKET_PORT = 1101;
    protected Socket socket;
    protected static NetworkSocket instance = null;
    private String IP;

    /**
     * This method is used to obtain a reference to the singleton class; this will be possible only if {@link client.network.NetworkSocketClient#setInstance()}
     * or {@link server.network.NetworkSocketServer#setInstance()} have been called before, otherwise a {@code null} pointer will be returned
     * @return NetworkRmi Returns an instance of an implementation of the {@link NetworkSocket} abstract class, as stated
     * in the class description
     */
    public static NetworkSocket getInstance() {
        return instance;
    }

    /**
     * This method is used to return the machine's IP over the local network
     * @return String Returns the machine's IP address encoded as a string
     */
    public String getIp() {
        return this.IP;
    }

    public NetworkSocket() {
        try {
            // Get local IP
            this.IP = InetAddress.getLocalHost().getHostAddress();
            //TODO Implement socket opening
        } catch (Exception e) {
            Logger.log("Something went wrong!");
            Logger.strace(e);
        }
    }
}
