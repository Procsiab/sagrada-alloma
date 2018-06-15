package server.threads;


import server.Config;
import server.connection.ProxyServer;
import server.threads.GameGenerator.GameGenerator1;
import server.threads.GameGenerator.GameGenerator2;
import shared.Logger;
import server.concurrency.ConcurrencyManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class MainServer {
    //create an object of MainServer
    private static final MainServer instance = new MainServer();
    public static ProxyServer middlewareServer = ProxyServer.getInstance();
    private static ArrayList<GameManager> gameManagers = new ArrayList<>();
    private static Integer gameManagerCode = 0;
    public static final Object obj = new Object();

    public static MainServer getInstance() {
        return instance;
    }

    public static Integer addGameManagerCode() {
        gameManagerCode++;
        return gameManagerCode;
    }

    public static ArrayList<GameManager> getGameManagers() {
        return gameManagers;
    }

    public static Integer addGameManagers(GameManager gameManager) {
        synchronized (obj) {
            MainServer.gameManagers.add(gameManager);
            obj.notifyAll();
        }
        return addGameManagerCode();
    }

    private MainServer() {
        super();
    }

    public static void simulation() {
        if (!Config.read()) {
            Logger.log("Can't read config. Server close now.");
            return;
        }

        ConcurrencyManager.submit(new GameGenerator1());
        ConcurrencyManager.submit(new GameGenerator2());

    }

    public static <T> T deepClone(T type) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(type);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static Integer primeNumber(int n) {
        int candidate, count;
        for (candidate = 2, count = 0; count < n; ++candidate) {
            if (isPrime(candidate)) {
                ++count;
            }
        }
        // The candidate has been incremented once after the count reached n
        return candidate - 1;
    }

    private static boolean isPrime(int n) {
        if (n % 2 == 0) return n == 2;
        if (n % 3 == 0) return n == 3;
        int step = 4;
        int m = (int) Math.sqrt(n) + 1;
        for (int i = 5; i < m; step = 6 - step, i += step) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        if (!Config.read()) {
            Logger.log("Can't read config. Server close now.");
            return;
        }

        ConcurrencyManager.submit(new GameGenerator1());
        ConcurrencyManager.submit(new GameGenerator2());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        System.out.println();
        Logger.log("Send 'exit' command to teardown...\n\n");
        Scanner scan = new Scanner(System.in);
        while (!scan.nextLine().equals("exit")) {
            //
        }
        ConcurrencyManager.shutdown();
        System.exit(0);
    }

}