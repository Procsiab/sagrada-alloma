package client.network;

import shared.Logger;
import shared.network.NetworkSocket;

public class NetworkSocketClient extends NetworkSocket{
    /**
     * This method will call the private constructor only if the attribute {@link NetworkSocketClient#instance} is not null.
     * This will ensure that there will be just one instance of {@link NetworkSocketClient}
     */
    public static void setInstance() {
        if(instance == null) {
            instance = new NetworkSocketClient();
        }
    }
    
    private NetworkSocketClient() {
        super();
        try {
            //TODO Open socket connection
        } catch (Exception e) {
            Logger.log("Something went wrong!");
            Logger.strace(e);
        }
    }
}
