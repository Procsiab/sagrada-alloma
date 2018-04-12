package client.network;

import client.MainClient;
import client.threads.Game;
import shared.SharedNetworkClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class Network implements SharedNetworkClient {
    private static final String SERVER_IP = "localhost";
    private static final String RMI_IFACE_NAME = "Network";
    private static final Integer RMI_PORT = 1099;
    private String clientIp;
    public static final Integer SOCKET_PORT = 1101;

    public Network (String[] args) {
        super();
        try {
            this.clientIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //launch(args);
    }


    private Game game = new Game();

    public void printMessage(String s) throws RemoteException{
        MainClient.printMessage(s);
    }
    public String getClientIp() throws RemoteException{
        return clientIp;
    }
}
