package server;

import server.threads.NewGameManager;
import shared.logic.ConcurrencyManager;
import shared.SharedClientGame;

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

    public static void main(String[] args) throws IOException {

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