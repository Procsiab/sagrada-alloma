package client.network;

import shared.network.NetworkSocket;

public class NetworkSocketClient extends NetworkSocket{
    /**
     * This method will call the private constructor only if the attribute {@link NetworkRmiClient#instance} is not null.
     * This will ensure that there will be just one instance of {@link NetworkRmiClient}
     */
    public static void setInstance() {
        if(instance == null) {
            instance = new NetworkSocketClient();
        }
    }
}
