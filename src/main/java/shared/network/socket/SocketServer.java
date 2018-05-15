package shared.network.socket;

import shared.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer implements Closeable, Runnable {
    private ServerSocket socketConsumer;
    private Integer port;
    private ExecutorService pool;
    private Map<String, Object> exportedObjects;
    private boolean runForever = true;

    SocketServer(Integer port, Map<String, Object> objects) {
        this.port = port;
        this.pool = Executors.newCachedThreadPool();
        this.exportedObjects = objects;
        prepareConnection();
    }

    private void prepareConnection() {
        try {
            // Setup the socket which will receive data from the client
            this.socketConsumer = new ServerSocket(port);
            Logger.log("Opened socket on port " + socketConsumer.getLocalPort());
        }  catch (IOException ioe) {
            Logger.log("Error while opening socket on port " + port.toString() + "!");
            Logger.strace(ioe);
        }
    }

    private Socket acceptConnection() {
        Socket clientCon = null;
        try {
            clientCon = socketConsumer.accept();
            Logger.log("Connection accepted from " + clientCon.getRemoteSocketAddress().toString());
        } catch (IOException ioe) {
            Logger.log("Error while accepting data on socket!");
            Logger.strace(ioe);
        }
        return clientCon;
    }

    @Override
    public void run() {
        do {
            Logger.log("Waiting for clients...");
            final Socket client = acceptConnection();
            pool.submit(new SocketHandler(client, exportedObjects));
        } while (runForever);
    }

    public Integer getPort() {
        return socketConsumer.getLocalPort();
    }

    @Override
    public void close() {
        try {
            this.socketConsumer.close();
        } catch (IOException ioe) {
            Logger.log("Error closing socket after stopping server");
        }
        this.pool.shutdown();
        this.runForever = false;
    }
}