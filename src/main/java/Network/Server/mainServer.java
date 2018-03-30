package Network.Server;

import Logic.Match;
import Logic.Player;
import Logic.PlayerRef;
import Network.Network;

import java.io.*;
import java.rmi.Naming;
import java.util.LinkedList;
import java.util.Queue;

public class mainServer {
    //create an object of mainServer
    private static final mainServer Instance = new mainServer();
    private static final Integer maxActivePlayer = 250;
    private static String[][] bindingConfig = new String[maxActivePlayer][3];
    private LinkedList<PlayerRef> pR = new LinkedList<PlayerRef>();
    private LinkedList<Player> p = new LinkedList<Player>();
    @Deprecated LinkedList<Match> m = new LinkedList<Match>();
    private Queue<PlayerRef> pp1 = new LinkedList<PlayerRef>();
    private Queue<PlayerRef> pp2 = new LinkedList<PlayerRef>();
    private Queue<PlayerRef> pp3 = new LinkedList<PlayerRef>();
    private Queue<PlayerRef> pp4 = new LinkedList<PlayerRef>();
    private Integer ActivePlayer = 0;

    //make the constructor private so that this class cannot be instantiated
    private mainServer() {
        //should be empty
        System.out.println("mainServer class instance was born");
    }

    //Get the only object available
    public static mainServer getInstance() {
        return Instance;
    }

    public LinkedList<PlayerRef> getpR(){
        return pR;
    }

    public LinkedList<Player> getP() {
        return p;
    }

    public LinkedList<Match> getM() {
        return m;
    }

    public int createAndBindUpd(String MAC, String IP, String Port, String Name, Integer nMates) {
        //this is called from ListeningChannel on new player request and create a temporary player reference

        //fill in the first empty place
        Integer k = 0;
        while (k < pR.size() && pR.get(k) != null)
            k++;
        PlayerRef playerRef = new PlayerRef(k, Name, nMates); //send IDPlayer to player. He will use that to introduce himself.
        pR.add(k,playerRef);

        bindingConfig[k][0] = MAC;
        bindingConfig[k][1] = IP;
        bindingConfig[k][2] = Port;
        System.out.println("Bound queued player to ID " + k.toString());

        if (nMates == 1) {
            pp1.add(playerRef);
        } else if (nMates == 2) {
            pp2.add(playerRef);
        } else if (nMates == 3) {
            pp3.add(playerRef);
        } else if (nMates == 4) {
            pp4.add(playerRef);
        }

        tryStartMatch();
        return k;
    }

    public void cancelAndUnbind(Integer IDPlayer){
        //remove playerRef from pR and from queue
    }

    public void tryStartMatch(){
        //check if there is a chance to play
    }

    public static void main(String args[]) throws IOException {
        // Create singleton instance
        mainServer server = mainServer.getInstance();
        try {
            // Create an instance of Network, which will have the role of server's interface
            Network netIface = new Network(server);

            // Format an URL string for that interface, to be used in RMI registry
            String rmiUrl = "//" + netIface.getServerIp() + ":" + Network.RMI_PORT.toString() + "/"
                    + Network.RMI_IFACE_NAME;

            // Bind the interface to that symbolic URL in the RMI registry
            Naming.rebind(rmiUrl, netIface);

        } catch (Exception e) { // Better exception handling
            e.printStackTrace();
        }

        // Start connection handling socket
        ListeningChannel listener = new ListeningChannel(Network.SOCKET_PORT);
        try {
            listener.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            listener.close();
        }
    }
}