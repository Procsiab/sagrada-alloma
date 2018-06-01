package server;


import server.threads.GameGenerator.GameGenerator2_3;
import server.threads.GameGenerator.GameGenerator4;
import shared.concurrency.ConcurrencyManager;
import shared.network.SharedMiddlewareServer;

import java.io.*;
import java.util.Scanner;

public class MainServer {
    //create an object of MainServer
    private static final MainServer instance = new MainServer();
    public static SharedMiddlewareServer middlewareServer = MiddlewareServer.getInstance();

    public static MainServer getInstance() {
        return instance;
    }

    private MainServer() {
        super();
    }

    public static void simulation() {
        ConcurrencyManager.submit(new GameGenerator2_3());
        ConcurrencyManager.submit(new GameGenerator4());

    }

    public static void main(String[] args) throws IOException {
        ConcurrencyManager.submit(new GameGenerator2_3());
        ConcurrencyManager.submit(new GameGenerator4());

        System.out.println("\nSend 'exit' command to teardown...");
        Scanner scan = new Scanner(System.in);
        while (!scan.nextLine().equals("exit")) {
            //
        }
        ConcurrencyManager.shutdown();
        System.exit(0);
    }
}