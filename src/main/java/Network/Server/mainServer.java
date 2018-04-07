package Network.Server;

import Logic.Concurrency.ListeningChannel;
import Logic.*;
import Network.Network;
import Network.Shared.SharedMainClient;

import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Vector;

public class mainServer {
    //create an object of mainServer
    private static final mainServer Instance = new mainServer();
    // List of players connected
    private static Vector<SharedMainClient> clients;

    public static mainServer getInstance() {
        return Instance;
    }

    private mainServer(){}

    public static void connect(SharedMainClient c) throws RemoteException {
        System.out.println("Someone connected, I'm not alone! =)");
        // Call method on the client: u w8 m8?!
        c.printMessage("SERVER: Th4t w4z bl4ck mag1c!");
    }

    public static void main(String args[]) throws IOException {
        try {
            // Create an instance of Network, which will have the role of server's interface
            Network netIface = new Network();

            // Format an URL string for that interface, to be used in RMI registry
            String rmiUrl = "//" + netIface.getServerIp() + ":" + Network.RMI_PORT.toString() + "/"
                    + Network.RMI_IFACE_NAME;

            // Bind the interface to that symbolic URL in the RMI registry
            Naming.rebind(rmiUrl, netIface);

        } catch (Exception e) { // Better exception handling
            e.printStackTrace();
        }

        System.out.println("Send 'exit' command to teardown...");
        Scanner scan = new Scanner(System.in);
        while (!scan.nextLine().equals("exit")) {
            //
        }
        ConcurrencyManager.ThreadManager.shutdown();
        System.exit(0);
    }
}