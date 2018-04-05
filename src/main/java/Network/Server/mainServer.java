package Network.Server;

import Logic.Concurrency.ListeningChannel;
import Logic.*;
import Network.Network;

import java.io.*;
import java.rmi.Naming;

public class mainServer {
    //create an object of mainServer
    private static final mainServer Instance = new mainServer();

    public static mainServer getInstance() {
        return Instance;
    }

    private mainServer(){}

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
        // Summon a ListeningChannel type thread
        ConcurrencyManager.submit(new ListeningChannel(Network.SOCKET_PORT));


        System.out.println("Press any key to teardown...");
        System.in.read(); // Hold on until a key press
        ConcurrencyManager.ThreadManager.shutdown();

    }
}