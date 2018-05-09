package server;


import server.threads.NewGameManager;
import shared.GameManager;
import shared.logic.ConcurrencyManager;
import shared.network.SharedMiddlewareServer;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class MainServer {
    //create an object of MainServer
    private static final MainServer instance = new MainServer();
    public static SharedMiddlewareServer middlewareServer = MiddlewareServer.getInstance();
    // List of players connected

    public static MainServer getInstance() {
        return instance;
    }

    private MainServer() {
        super();
    }

    public static void main(String[] args) throws IOException {
        MiddlewareServer.getInstance();
        MatchManager.getInstance();// delete this if newGameManager access MatchManager
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