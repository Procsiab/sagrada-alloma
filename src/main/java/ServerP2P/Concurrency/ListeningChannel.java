package ServerP2P.Concurrency;

import ClientP2P.Logic.ConcurrencyManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListeningChannel extends GeneralTask {

    private final ServerSocket mySocket;

    public ListeningChannel(int port) throws IOException {
        this.mySocket = new ServerSocket(port);
    }

    //@SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        System.out.println("Listener started");

        while (true) {
            try {
                // Wait until someone connects
                Socket clientSocket = mySocket.accept();
                // Start client handling
                ConcurrencyManager.submit(new SerializedSocket(clientSocket));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void close() throws IOException {
        mySocket.close();
    }
}