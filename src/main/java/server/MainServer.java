package server;

import server.threads.NewGameManager;
import shared.logic.ConcurrencyManager;
import shared.SharedClientGame;
import shared.network.ConnectionNetwork;
import shared.network.rmi.NetworkRmi;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class MainServer {
    //create an object of MainServer
    private static final MainServer instance = new MainServer();
    // List of players connected
    private static Vector<SharedClientGame> clients;

    public static MainServer getInstance() {
        return instance;
    }

    private MainServer(){
        super();
    }

    public static void main(String args[]) throws IOException {
        // Create ConnectionNetwork singleton to setup RMI connection
        ConnectionNetwork.setConnection(new NetworkRmi());
        // Create MatchManager singleton
        MatchManager.setInstance();
        // Export MatchManager for serving clients (abstraction from RMI or Socket)
        ConnectionNetwork.getConnection().export(MatchManager.getInstance(), "MatchManager");
        //create thread NewGameManager
        ConcurrencyManager.submit(new NewGameManager());

        System.out.println("\nSend 'exit' command to teardown...");
        Scanner scan = new Scanner(System.in);
        while (!scan.nextLine().equals("exit")) {
            //
        }
        ConcurrencyManager.shutdown();
        System.exit(0);
    }
}