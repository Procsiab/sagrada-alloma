package server;

import server.logic.*;
import server.network.NetworkServer;
import server.threads.NewGameManager_2;
import server.threads.NewGameManager_3;
import server.threads.NewGameManager_4;
import shared.SharedClientGame;

import java.io.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Vector;

public class MainServer {
    //create an object of MainServer
    private static final MainServer instance = new MainServer();
    // List of players connected
    private static Vector<SharedClientGame> clients;
    // RMI Registry ref
    private static Registry rmiRegistry;

    public static MainServer getInstance() {
        return instance;
    }

    private MainServer(){}

    public static void main(String args[]) throws IOException {
        try {
            // Start RMI registry on this machine
            rmiRegistry = LocateRegistry.createRegistry(NetworkServer.RMI_PORT);

            MatchManager.setInstance();
            // Format an URL string for that interface, to be used in RMI registry
            String rmiUrl = "//" + MatchManager.getInstance().getServerIp() + ":" + NetworkServer.RMI_PORT.toString() + "/"
                    + "Match";
            // Bind the interface to that symbolic URL in the RMI registry
            Naming.rebind(rmiUrl, MatchManager.getInstance());

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