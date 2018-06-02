package server;


import server.threads.GameGenerator.GameGenerator1;
import server.threads.GameGenerator.GameGenerator2;
import server.threads.GameManager;
import shared.concurrency.ConcurrencyManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MainServer {
    //create an object of MainServer
    private static final MainServer instance = new MainServer();
    public static MiddlewareServer middlewareServer = MiddlewareServer.getInstance();
    private static ArrayList<GameManager> gameManagers = new ArrayList<>();
    public static final Object obj = new Object();

    public static MainServer getInstance() {
        return instance;
    }

    public static ArrayList<GameManager> getGameManagers() {
        return gameManagers;
    }

    public static void addGameManagers(GameManager gameManager) {
        synchronized (obj) {
            MainServer.gameManagers.add(gameManager);
            obj.notifyAll();
        }
    }

    private MainServer() {
        super();
    }

    public static void simulation() {
        ConcurrencyManager.submit(new GameGenerator1());
        ConcurrencyManager.submit(new GameGenerator2());

    }

    public static void main(String[] args) throws IOException {
        ConcurrencyManager.submit(new GameGenerator1());
        ConcurrencyManager.submit(new GameGenerator2());

        System.out.println("\nSend 'exit' command to teardown...");
        Scanner scan = new Scanner(System.in);
        while (!scan.nextLine().equals("exit")) {
            //
        }
        ConcurrencyManager.shutdown();
        System.exit(0);
    }
}