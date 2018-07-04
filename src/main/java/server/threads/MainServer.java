package server.threads;


import server.Config;
import server.connection.ProxyServer;
import shared.Logger;
import server.concurrency.ConcurrencyManager;
import shared.network.rmi.NetworkRmi;

import java.io.*;
import java.util.Scanner;


public class MainServer {
    //create an object of MainServer
    private static final MainServer instance = new MainServer();
    private static ProxyServer proxyServer;

    public static MainServer getInstance() {
        return instance;
    }

    private MainServer() {
        super();
    }

    /**
     * emulate the mainserver to allow testing
     */
    public static void simulation() {
        if (!Config.read()) {
            Logger.log("Can't read config. Server close now.");
            return;
        }

        ConcurrencyManager.submit(new GameGenerator());

    }

    /**
     * @return a real copy of object of type
     * @param <T>
     * */
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

    public static void main(String[] args) {

        if (!Config.read()) {
            Logger.log("Can't read config. Server close now.");
            return;
        }

        if (args.length > 0) {
            NetworkRmi.setServerAddress(args[0]);
        }
        proxyServer = ProxyServer.getInstance();

        ConcurrencyManager.submit(new GameGenerator());

        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
            Logger.strace(ie);
            Thread.currentThread().interrupt();
        }

        System.out.println();
        Logger.log("Send 'exit' command to teardown...");
        Scanner scan = new Scanner(System.in);
        while (!scan.nextLine().equals("exit")) {
            //
        }
        ConcurrencyManager.shutdown();
        System.exit(0);
    }

}