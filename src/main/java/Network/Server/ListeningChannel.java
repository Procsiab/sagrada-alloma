package Network.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListeningChannel {
    private final ServerSocket mySocket;
    private final ExecutorService myPool;

    public ListeningChannel(int port) throws IOException {
        mySocket = new ServerSocket(port);
        myPool = Executors.newCachedThreadPool();
    }

    private void handleClient(Socket s) throws IOException, ClassNotFoundException {
        // Initialize input and output streams
        ObjectOutputStream objectOut = new ObjectOutputStream(s.getOutputStream());
        objectOut.flush();
        ObjectInputStream objectIn = new ObjectInputStream(s.getInputStream());

        // Receive player data
        String[] answer;
        answer = (String[]) objectIn.readObject();
        System.out.println("Client " + answer[3] + " has connected!");

        // Create player with mainServer and obtain the player ID
        Integer id = mainServer.getInstance().createAndBindUpd(answer[0], answer[1], answer[2], answer[3], Integer.parseInt(answer[4]));

        // Send back player ID to client
        objectOut.writeObject(new String[]{id.toString()});

        // Finalize connection
        objectIn.close();
        objectOut.close();
        s.close(); // Should start a session instead of closing...
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        System.out.println("Listener started");
        while (true) {
            try {
                Socket clientSocket = mySocket.accept();
                myPool.submit(() -> {
                    try {
                        handleClient(clientSocket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void close() throws IOException {
        mySocket.close();
        myPool.shutdown();
    }
}