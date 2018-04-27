package server.network;

import shared.network.NetworkSocket;

public class NetworkSocketServer extends NetworkSocket {
    /**
     * This method will call the private constructor only if the attribute {@link NetworkRmiServer#instance} is not null.
     * This will ensure that there will be just one instance of {@link NetworkRmiServer}
     */
    public static void setInstance() {
        if(instance == null) {
            instance = new NetworkSocketServer();
        }
    }
}
