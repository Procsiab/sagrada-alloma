package shared.network.socket;

import shared.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h1>Socket Server</h1>
 * <p>This class implements {@code Runnable} to make its instances assignable to a background {@code Thread}; moreover,
 * it implements {@code AutoCloseable} to gracefully terminate without user interaction the network components if needed</p><br>
 * <p>Instances of this class work by looping in the {@code run} method, waiting for an incoming connection: when a client
 * is accepted, its socket is passed as the parameter of the {@link SocketHandler} constructor; the constructed object is
 * then submitted to the {@code ExecutorService pool} attribute's thread pool</p>
 * @see NetworkSocket
 * @see SocketHandler
 * @see shared.network.Connection
 */
public class SocketServer implements AutoCloseable, Runnable {
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
        }  catch (IOException ioe) {
            Logger.log("Error while opening socket on port " + port.toString() + "!");
        }
    }

    private Socket acceptConnection() {
        Socket clientCon = null;
        try {
            clientCon = socketConsumer.accept();
        } catch (IOException ioe) {
            Logger.log("Error while accepting data on socket!");
        }
        return clientCon;
    }

    @Override
    public void run() {
        do {
            final Socket client = acceptConnection();
            if (Thread.currentThread().isInterrupted()) {
                close();
            } else {
                pool.submit(new SocketHandler(client, exportedObjects));
            }
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