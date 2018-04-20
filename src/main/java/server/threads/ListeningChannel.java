package server.threads;

import server.network.SerializedSocket;
import shared.Logger;
import shared.logic.ConcurrencyManager;
import shared.logic.GeneralTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListeningChannel extends GeneralTask {

    public final ServerSocket mySocket;

    public ListeningChannel(int port) throws IOException {
        this.mySocket = new ServerSocket(port);
    }

    @SuppressWarnings("InfiniteLoopStatement")
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
                Logger.log("Error reading socket buffer!");
                Logger.strace(e);
                break;
            }
        }
    }

    public void close() throws IOException {
        mySocket.close();
    }
}