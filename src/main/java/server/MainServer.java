package server;

import server.logic.*;
import server.network.NetworkServer;
import server.threads.NewGameManager_2;
import server.threads.NewGameManager_3;
import server.threads.NewGameManager_4;
import shared.SharedNetworkClient;

import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Vector;

public class MainServer {
    //create an object of MainServer
    private static final MainServer Instance = new MainServer();
    // List of players connected
    private static Vector<SharedNetworkClient> clients;

    public static MainServer getInstance() {
        return Instance;
    }

    private MainServer(){}

    public static void connect(SharedNetworkClient c, Integer n) throws RemoteException {
        System.out.println("Someone connected, nMates: " + n.toString());
        // Call method on the client: u w8 m8?!
        c.printMessage("SERVER: Th4t w4z bl4ck mag1c!");
    }

    public static void main(String args[]) throws IOException {
        try {
            // Create an instance of ServerP2P.NetworkServer, which will have the role of server's interface
            NetworkServer.setInstance();

            // Format an URL string for that interface, to be used in RMI registry
            String rmiUrl = "//" + NetworkServer.getInstance().getServerIp() + ":" + NetworkServer.RMI_PORT.toString() + "/"
                    + NetworkServer.RMI_IFACE_NAME;

            // Bind the interface to that symbolic URL in the RMI registry
            Naming.rebind(rmiUrl, NetworkServer.getInstance());

        } catch (Exception e) { // Better exception handling
            e.printStackTrace();
        }

        //create 3 threads NewGameManager
        ConcurrencyManager.submit(new NewGameManager_2());
        ConcurrencyManager.submit(new NewGameManager_3());
        ConcurrencyManager.submit(new NewGameManager_4());

        System.out.println("Send 'exit' command to teardown...");
        Scanner scan = new Scanner(System.in);
        while (!scan.nextLine().equals("exit")) {
            //
        }
        ConcurrencyManager.shutdown();
        System.exit(0);
    }
}