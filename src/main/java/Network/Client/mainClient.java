package Network.Client;

import Network.Shared.SharedNetwork;

import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class mainClient {
    private static final String SERVER_IP = "localhost";
    public static final Integer RMI_PORT = 1099;
    public static final Integer SOCKET_PORT = 1101;
    public static final String RMI_IFACE_NAME = "Network";

    private static String[] SocketSend(String[] message) throws IOException, ClassNotFoundException {
        Socket myClient = null;
        String[] answer = {""};
        try {
            // Initialize socket
            myClient = new Socket(SERVER_IP, SOCKET_PORT);

            // Initialize input and output streams
            ObjectOutputStream objectOut = new ObjectOutputStream(myClient.getOutputStream());
            objectOut.flush(); // Very important to flush right after creation
            ObjectInputStream objectIn = new ObjectInputStream(myClient.getInputStream());

            // Send the message (serialize)
            objectOut.writeObject(message);

            // Get the answer (deserialize)
            answer = (String[]) objectIn.readObject();

            // Finalize connection
            objectIn.close();
            objectOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            myClient.close(); // Should start a session instead of closing
        }
        return answer;
    }

    public static void main(String [] args) throws NotBoundException, IOException, ClassNotFoundException {
        // Look for the RMI registry on specific server port
        Registry rmiRegistry = LocateRegistry.getRegistry(SERVER_IP, RMI_PORT);

        // Get a reference to the remote instance of Network, through SaredNetwork interface
        SharedNetwork netIface = (SharedNetwork) rmiRegistry.lookup(RMI_IFACE_NAME);

        // Get these values from system and graphic interface interaction
        // here they are generated depending on the client command line argument
        String[] clientInfo = {""};
        switch (Integer.parseInt(args[0])) {
            case 1:
                clientInfo = new String[]{"AA:AA:AA:AA", "1.1.1.1", "111", "Asdrubale", "3"};
                break;
            case 2:
                clientInfo = new String[]{"BB:BB:BB:BB", "2.2.2.2", "222", "Temistocle", "3"};
                break;
            case 3:
                clientInfo = new String[]{"CC:CC:CC:CC", "3.3.3.3", "333", "Roborbio", "3"};
                break;
            default:
                break;
        }
        // Create a socket and try to open it towards server
        String[] authResponse = SocketSend(clientInfo);

        //Decode the String[] message to obtain following values
        Integer playerId = Integer.parseInt(authResponse[0]);

        System.out.println("PlayerID: " + playerId.toString());
        System.out.println("Contents of LinkedList<PlayerRef>:\n" + netIface.getPlayerRefList());
    }
}