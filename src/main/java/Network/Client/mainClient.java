package Network.Client;

import Network.Shared.SharedNetwork;

import java.io.*;
import java.net.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class mainClient {
    private static final String SERVER_IP = "localhost";
    public static final Integer RMI_PORT = 1099;
    public static final String RMI_IFACE_NAME = "Network";


    public static void main(String [] args) throws RemoteException, NotBoundException {

            String serverName = args[0];
            int port = Integer.parseInt(args[1]);
            try {
                System.out.println("Connecting to " + serverName + " on port " + port);
                Socket client = new Socket(serverName, port);

                System.out.println("Just connected to " + client.getRemoteSocketAddress());
                OutputStream outToServer = client.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);

                out.writeUTF("Hello from " + client.getLocalSocketAddress());
                InputStream inFromServer = client.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);

                System.out.println("Server says " + in.readUTF());
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }






        // Look for the RMI registry on specific server port
        Registry rmiRegistry = LocateRegistry.getRegistry(SERVER_IP, RMI_PORT);
        // Get a reference to the remote instance of Network, through SaredNetwork interface
        SharedNetwork netIface = (SharedNetwork) rmiRegistry.lookup(RMI_IFACE_NAME);
        //Get these values from system and graphic interface interaction
        String mac = "DE:AD:BE:EF";
        String ip = "1.1.1.1";
        String Port = "666";
        Integer nmates = Integer.parseInt("6");
        String name = "Asdrubale";

        //Send those values to server







    }
}