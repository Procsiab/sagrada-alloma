package Network.Client;

import Network.Shared.SharedMainClient;
import Network.Shared.SharedNetwork;

import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class mainClient extends UnicastRemoteObject implements SharedMainClient {
    private static final String SERVER_IP = "localhost";
    public static final Integer RMI_PORT = 1099;
    public static final Integer SOCKET_PORT = 1101;
    public static final String RMI_IFACE_NAME = "Network";

    public mainClient() throws RemoteException {

    }

    public void printMessage(String s) {
        System.out.println(s);
    }

    public static void main(String [] args) throws NotBoundException, IOException, ClassNotFoundException {
        try {
            // Look for the RMI registry on specific server port
            Registry rmiRegistry = LocateRegistry.getRegistry(SERVER_IP, RMI_PORT);
            // Get a reference to the remote instance of Network, through SaredNetwork interface
            SharedNetwork netIface = (SharedNetwork) rmiRegistry.lookup(RMI_IFACE_NAME);
            // Connect through RMI call
            System.out.println("Connecting...");
            netIface.connect(new mainClient());

            // Close connection on command
            Scanner scan = new Scanner(System.in);
            while (!scan.nextLine().equals("exit")) {
                //
            }
            System.exit(0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}